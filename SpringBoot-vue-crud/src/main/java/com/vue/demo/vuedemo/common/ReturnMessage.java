package com.vue.demo.vuedemo.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;

/**
 * 标准返回
 *
 * @author wangguichao
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnMessage<T> {

    /**
     * 返回代码
     */
    int code;
    Integer subCode;

    /**
     * 返回信息
     */
    String msg;
    String subMsg;

    /**
     * 返回数据
     */
    T data;
    T subData;

    /**
     * 标记，时间戳
     */
    Long stamp;

    public ReturnMessage() {
    }

    public ReturnMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnMessage(int code, String msg, Long stamp) {
        this.code = code;
        this.msg = msg;
        this.stamp = stamp;
    }

    public ReturnMessage(int code, int subCode, String msg) {
        this.code = code;
        this.subCode = subCode;
        this.msg = msg;
    }

    public ReturnMessage(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnMessage(int code, int subCode, String msg, String subMsg, T data, T subData) {
        this.code = code;
        this.subCode = subCode;
        this.msg = msg;
        this.subMsg = subMsg;
        this.data = data;
        this.subData = subData;
    }

    public ReturnMessage(int code, Integer subCode, String msg, String subMsg, T data, T subData, Long stamp) {
        this.code = code;
        this.subCode = subCode;
        this.msg = msg;
        this.subMsg = subMsg;
        this.data = data;
        this.subData = subData;
        this.stamp = stamp;
    }


}
