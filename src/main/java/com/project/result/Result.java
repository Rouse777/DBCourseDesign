package com.project.result;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value = "响应json数据模型",description = "后端响应数据的统一封装格式")
public class Result {
    @ApiModelProperty("SUCCESS(2000,\"成功\"),\n" +
            "FAIL(4000,\"失败\"),\n" +
            "ILLEGAL_ARGS(4001,\"参数格式不正确\"),\n" +
            "UNAUTHENTICATED(4002,\"尚未登录\"),\n" +
            "UNAUTHORIZED(4003,\"操作未授权\"),\n" +
            "NO_CONTROLLER(4004,\"没找到响应控制器\"),\n" +
            "INTERNAL_ERROR(5000,\"服务器内部出错\");")
    private int code;

    @ApiModelProperty("详细说明")
    private String msg;

    @ApiModelProperty("数据")
    private Map<String, Object> data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
        data = new HashMap<>();
    }

    public Result(int code, String msg,Object object) {
        this.code = code;
        this.msg = msg;
        data = new HashMap<>();
        data.put(msg,object);
    }


    public Result(int code) {
        this.code = code;
        for (ResultCode rc : ResultCode.values()) {
            if (code == rc.code()) {
                msg = rc.msg();
                break;
            }
        }
        data = new HashMap<>();
    }

    public static Result success(String msg) {
        return new Result(ResultCode.SUCCESS.code(), msg);
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.code());
    }

    public static Result fail(String msg) {
        return new Result(ResultCode.FAIL.code(), msg);
    }

    public static Result fail() {
        return new Result(ResultCode.FAIL.code());
    }

    public static Result any(ResultCode code, String msg) {
        return new Result(code.code(), msg);
    }

    public static Result any(ResultCode code) {
        return new Result(code.code());
    }

    public Result put(String key, Object value) {
        data.put(key, value);
        return this;
    }
}
