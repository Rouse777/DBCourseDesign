package com.project.config;

import com.project.po.UserPO;
import com.project.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashSet;

//自定义realm，shiro从Realm里获取安全数据
//意思就是自己写一个类继承AuthorizingRealm类，然后重写AuthorizingRealm的getAuthorizationInfo方法
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    //重写授权方法doGetAuthorizationInfo
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取用户
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //通过用户名从数据库获取角色字符串（一个用户只有一个角色）
        String role = userService.getRoleByName(username);
        //添加角色
        info.setRoles(new HashSet<>(Collections.singletonList(role)));
        return info;
    }

    //重写认证方法doGetAuthenticationInfo
    //用户登录时会调用这个方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 从token里获取身份信息：用户名username，token代表用户输入的信息
        // principal : 主体的标示，可以有多个，但是需要具有唯一性，常见的有用户名，手机号，邮箱等
        String username = token.getPrincipal().toString();
        //根据用户名（唯一标识）从数据库里查到这个用户
        UserPO userInDB = userService.getByName(username);
        if (userInDB == null) throw new UnknownAccountException("用户名不存在");
        String passwordInDB = userInDB.getPassword();       //取出这个用户的密码
        //取出这个用户的盐
        String salt = userInDB.getSalt();
        //SimpleAuthenticationInfo ：代表该用户的认证信息。参数为“用户名+密码+盐”
        //this.getName()是获取CachingRealm的名字
        return new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
    }

}

