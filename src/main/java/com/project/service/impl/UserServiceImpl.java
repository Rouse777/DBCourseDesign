package com.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.config.ShiroConfig;
import com.project.dto.AuthUser;
import com.project.dto.UserInfo;
import com.project.mapper.UserMapper;
import com.project.po.UserPO;
import com.project.po.UserRole;
import com.project.service.UserService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    private Wrapper<UserPO> nameWrapper(String username) {
        return new QueryWrapper<UserPO>().eq("username", username);
    }

    @Override
    public UserPO getByName(String username) {
        return super.getOne(nameWrapper(username));
    }

    @Override
    public boolean isNameExist(String username) {
        return super.count(nameWrapper(username)) != 0;
    }

    @Override
    public String getRoleByName(String username) {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.select("role").eq("username", username);
        UserPO one = super.getOne(wrapper);
        return one.getRole();
    }

    @Override
    public boolean removeByName(String username) {
        return super.remove(nameWrapper(username));
    }

    @Override
    public List<UserInfo> listUserInfo() {
        QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
        wrapper.select("username", "role");
        List<UserPO> userPOs = super.list(wrapper);
        return toUserInfoList(userPOs);
    }

    private List<UserInfo> toUserInfoList(List<UserPO> userPOs) {
        ArrayList<UserInfo> userInfos = new ArrayList<>();
        for (UserPO userPO : userPOs) {
            userInfos.add(userPO.getUserInfo());
        }
        return userInfos;
    }


    /**
     * 注册核心方法，利用shiro对密码进行盐值哈希加密后存入数据库
     * @param authUser 封装了要保存的用户名和密码信息
     * @return 若成功保存返回true，若该用户名已存在则返回false
     */
    public boolean saveAuthUser(AuthUser authUser) {
        String username = authUser.getUsername();
        String password = authUser.getPassword();
        //username = HtmlUtils.htmlEscape(username);//防止恶意注册携带html标签
        UserPO userPO = new UserPO();
        userPO.setUsername(username);

        //判断用户名是否已经存在，如果已存在，那就返回错误信息
        if (this.isNameExist(username)) return false;

        //SecureRandomNumberGenerator类随机方法创建盐，进行两次加密，加密算法用md5
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();

        //SimpleHash类使用md5加密算法加密两次，把盐加进去，生成新的密码
        String encodedPassword =
                new SimpleHash(ShiroConfig.HASH_ALGORITHM, password, salt, ShiroConfig.HASH_ITERATIONS).toString();

        //把盐和生成的加密密码，存到数据库里
        userPO.setSalt(salt);
        userPO.setPassword(encodedPassword);
        userPO.setRole(UserRole.ROLE_USER);//只能注册普通用户
        return super.save(userPO);
    }
}
