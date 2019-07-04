package com.example.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @create: 2019-07-03 22:54
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity  //开启 Spring Security 权限控制和认证功能。
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    //添加角色
    // 当配置文件和 SecurityConfig 类中都配置了用户名和密码时，会使用代码中的用户名和密码。
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("user")
                .password(new BCryptPasswordEncoder()
                        .encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(new BCryptPasswordEncoder()
                .encode("123456")).roles("ADMIN")
                ;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/css/**", "/").permitAll()//配置不用登录可以访问的请求。
                //.antMatchers("/", "/home")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/content/**").hasAnyRole("ADMIN","USER") // 参数中任意角色的用户可访问  == .access("hasRole('ADMIN') or hasRole('USER')")
                .anyRequest().authenticated()  //表示其他的请求都必须要有权限认证。
                .and()
                .formLogin() //定制登录信息。
                //.loginPage("/login")//自定义登录地址，若注释掉则使用默认登录页面。
                //.successForwardUrl("/index") //登陆成功跳转url
                //.failureForwardUrl("/login?error")//失败跳转url
                .permitAll()
                .and()
                .logout() //默认是logout登出
                .permitAll()
                .and()
                .csrf()
                .ignoringAntMatchers("/logout");

    }
}
