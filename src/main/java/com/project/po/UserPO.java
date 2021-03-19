package com.project.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.project.dto.UserInfo;
import lombok.Data;

/**
 * user的PO类，用于与数据库交互
 */
@Data
@TableName("user")
public class UserPO {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String role;
    private String salt;

    public UserInfo getUserInfo(){
        return new UserInfo(username,role);
    }
}
