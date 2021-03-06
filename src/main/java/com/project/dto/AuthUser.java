package com.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * user的DTO类，与前端交互，用于登录和注册时接收前端传来的用户名和密码，并用Validation对数据作验证
 */
@Data
@ApiModel(value = "登录用户模型",description = "进行登录、注册和注销时，用于接收用户名和密码")
public class AuthUser {
    @Pattern(regexp = "\\w{3,16}", message = "用户名，由数字、字母或下划线组成，长度3-16个字符")
    @ApiModelProperty("用户名，由数字、字母或下划线组成，长度3-16个字符")
    private String username;

    @ApiModelProperty("密码，长度3-16个字符")
    @NotNull
    @Length(min = 3, max = 16, message = "密码，长度3-16个字符")
    private String password;

    public AuthUser(){}

    public AuthUser(String a,String b){this.username=a;this.password=b;};

}
