package com.wangjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangjy.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface UserService extends IService<UserPO> {
    UserPO getByName(String userName);

    boolean isNameExist(String username);

    String getRoleByName(@Param("username") String username);
}
