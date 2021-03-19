package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.dto.AuthUser;
import com.project.dto.UserInfo;
import com.project.po.UserPO;

import java.util.List;

public interface UserService extends IService<UserPO> {
    UserPO getByName(String userName);

    boolean isNameExist(String username);

    String getRoleByName(String username);

    boolean removeByName(String name);

    List<UserInfo> listUserInfo();

    boolean saveAuthUser(AuthUser authUser);
}
