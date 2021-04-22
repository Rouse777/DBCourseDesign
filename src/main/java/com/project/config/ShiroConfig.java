package com.project.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    public static final String HASH_ALGORITHM = "md5";
    public static final int HASH_ITERATIONS = 2;

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("user", new StatelessAuthcFilter());
        shiroFilterFactoryBean.setFilters(filters);


//        //未登录时跳转
//        shiroFilterFactoryBean.setLoginUrl("/unauthenticated");
//        //授权失败时跳转，即权限不足
//        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // authc:必须登录才可以访问;
        // anon:任何人都可以访问;
        // roles[ROLE_ADMIN]:只有admin才能访问
        // roles[ROLE_USER]:只有user才能访问

        //注册登录放行

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");

        //swagger2放行
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/configuration/**", "anon");

        //权限测试设置
        filterChainDefinitionMap.put("/check-login", "anon");
        filterChainDefinitionMap.put("/admin/**", "anon");

        //剩余的都需要认证(这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截)
        //测试时将权限改为"anon"避免每次需要登录，发布时改为"authc"



        filterChainDefinitionMap.put("/**", "anon");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        Cookie cookie = new SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME);

        //安全字段设置
        cookie.setHttpOnly(false);
        cookie.setSameSite(Cookie.SameSiteOptions.NONE);
        //cookie.setSecure(false);

        sessionManager.setSessionIdCookie(cookie);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public MyRealm myRealm() {
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myRealm;
    }

    /*设置使用md5两次加密*/
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(HASH_ALGORITHM);
        hashedCredentialsMatcher.setHashIterations(HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }
}

