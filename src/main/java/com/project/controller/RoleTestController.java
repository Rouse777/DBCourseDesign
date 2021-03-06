package com.project.controller;

import com.project.po.UserPO;
import com.project.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户权限测试")
@RestController
public class RoleTestController {
    @GetMapping("/test/user")
    @ApiOperation("登录或记住的用户可以访问")
    public Result userTest() {
        return Result.success("您已登录，可以访问");
    }

    @GetMapping("/test/role-admin")
    @ApiOperation("管理员可以访问")
    public Result roleAdminTest() {
        return Result.success("您是管理员，可以访问");
    }

    @GetMapping("/test/role-user")
    @ApiOperation("普通用户可以访问")
    public Result roleUserTest() {
        return Result.success("您是普通用户，可以访问");
    }

}
