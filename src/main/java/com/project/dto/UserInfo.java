package com.project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value = "用户信息模型", description = "用于传输用户信息")
public class UserInfo{
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("角色：ROLE_USER或ROLE_ADMIN")
    private String role;
}
