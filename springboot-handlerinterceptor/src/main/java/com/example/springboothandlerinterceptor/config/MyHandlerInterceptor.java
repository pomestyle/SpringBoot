package com.example.springboothandlerinterceptor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description: 自定义拦截器
 * @author: Administrator
 * @create: 2019-07-06 18:18
 **/

public class MyHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(MyHandlerInterceptor.class);

    /**
     * @description: 拦截在控制方法之前拦截
     *
     * ，当某个 URL 已经匹配到对应的 Controller 中的某个方法，且在这个方法执行之前。
     *  所以 preHandle(……) 方法可以决定是否将请求放行，这是通过返回值来决定的，返回 true 则放行，返回 false 则不会向后执行。
     *
     * @author: wangl
     * @create: 2019-07-06 18:19
     * @version 1.0
     **/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();
        logger.info("==== preHandle =====   拦截到了方法：{}，在该方法执行之前执行====",methodName);

        System.out.println(((HandlerMethod) handler).getMethod().getName());
        // 返回 true 才会继续执行，返回 false 则取消当前请求



        // 判断用户有没有登陆，一般登陆之后的用户都有一个对应的 token
        String token = request.getParameter("token");
        if (null == token || "".equals(token)) {
            logger.info("用户未登录，没有权限执行……请登录");
            response.getWriter().println("error");
            return false;
        }

        // 返回 true 才会继续执行，返回 false 则取消当前请求
        return true;
    }

    /**
     * @description: 在渲染页面之前拦截
        该方法的执行时机是，当某个 URL 已经匹配到对应的 Controller 中的某个方法，且在执行完了该方法，但是在 DispatcherServlet 视图渲染之前。
        所以在这个方法中有个 ModelAndView 参数，可以在此做一些修改动作。
     * @author: wangl
     * @create: 2019-07-06 18:20
     * @version 1.0
     **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("=================== postHandle ================== 执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    /**
     * @description: 在控制方法正确返回后拦截
     *
     * 该方法是在整个请求处理完成后（包括视图渲染）执行，这时做一些资源的清理工作，
     * 这个方法只有在 preHandle(……) 被成功执行后并且返回 true 才会被执行。
     * @author: wangl
     * @create: 2019-07-06 18:20
     * @version 1.0
     **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("============ afterCompletion =================== 整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}
