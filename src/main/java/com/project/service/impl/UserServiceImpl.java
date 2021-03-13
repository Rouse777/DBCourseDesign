package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.mapper.UserMapper;
import com.project.po.UserPO;
import com.project.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserPO getByName(String username) {
        return super.getOne(nameWrapper(username));
    }

    private Wrapper<UserPO> nameWrapper(String username) {
        return new QueryWrapper<UserPO>().eq("username", username);
    }

    @Override
    public boolean isNameExist(String username) {
        return super.count(nameWrapper(username)) != 0;
    }

    @Override
    public String getRoleByName(String username) {
        return userMapper.selectRoleByName(username);
    }
}
