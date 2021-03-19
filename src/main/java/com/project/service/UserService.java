package com.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.po.UserPO;
import org.apache.ibatis.annotations.Param;

public interface UserService extends IService<UserPO> {
    UserPO getByName(String userName);

    boolean isNameExist(String username);

    String getRoleByName(String username);

    boolean removeByName(String name);
}
