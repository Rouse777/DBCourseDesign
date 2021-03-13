package com.wangjy.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public enum ResultCode {
    SUCCESS(2000,"成功"),
    FAIL(4000,"失败"),
    ILLEGAL_ARGS(4001,"参数格式不正确"),
    UNAUTHENTICATED(4002,"尚未登录"),
    UNAUTHORIZED(4003,"操作未授权"),
    NO_CONTROLLER(4004,"没找到响应控制器"),
    INTERNAL_ERROR(5000,"服务器内部出错");

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    private int code;
    private String msg;
}
