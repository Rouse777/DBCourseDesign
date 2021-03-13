package com.project.controller;

import com.project.dto.UserDTO;
import com.project.po.UserPO;
import com.project.result.Result;
import com.project.result.ResultCode;
import com.project.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
@Api(tags = "普通用户注册登录")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //shiro加持的注册方法
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public Result register(@RequestBody @Valid UserDTO userDTO) {
        log.info("用户{}请求注册", userDTO);
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        //username = HtmlUtils.htmlEscape(username);//防止恶意注册携带html标签
        UserPO userPO = new UserPO();
        userPO.setUsername(username);

        //判断用户名是否已经存在，如果已存在，那就返回错误信息
        if (userService.isNameExist(username)) {
            log.info("用户{}注册失败，用户名已被注册", userDTO);
            //返回失败信息
            return Result.fail("用户名已经被注册！");
        }

        //SecureRandomNumberGenerator类随机方法创建盐，进行两次加密，加密算法用md5
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        int times = 2;//可实现从配置文件读
        String algorithm = "md5";

        //SimpleHash类使用md5加密算法加密两次，把盐加进去，生成新的密码
        String encodedPassword = new SimpleHash(algorithm, password, salt, times).toString();

        //把盐和生成的加密密码，存到数据库里
        userPO.setSalt(salt);
        userPO.setPassword(encodedPassword);
        log.info(userPO.toString());
        userService.save(userPO);
        log.info("用户{}注册成功", userDTO);
        //返回 成功信息
        return Result.success("注册成功");
    }

    //shiro加持的登录方法，参数传进来一个UserDTO用户对象
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result login(@RequestBody @Valid UserDTO userDTO, @ApiIgnore HttpSession session) {
        //获取登录时输入的用户名
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        Subject subject = SecurityUtils.getSubject();
        //把输入的用户名+密码，放在token里面
        UsernamePasswordToken token =
                new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            UserPO userInDB = userService.getByName(username);
            session.setAttribute("user", userInDB);
            log.info("用户{}登录成功", userDTO);
            return Result.success("登录成功");
        } catch (UnknownAccountException e) {
            log.info("用户{}登录失败，用户名不存在", userDTO);
            return Result.fail("登录失败，用户名不存在");
        } catch (IncorrectCredentialsException e) {
            log.info("用户{}登录失败，用户名或密码错误", userDTO);
            return Result.fail("登录失败，用户名或密码错误");
        } catch (AuthenticationException e) {
            log.info("用户{}登录失败，未知原因", userDTO);
            return Result.fail("登录失败，未知原因");
        }
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        log.info("logout:{}", subject.getPrincipal());
        if (subject.isAuthenticated())
            subject.logout();
        return Result.success("登出成功");
    }

    @GetMapping("check-login")
    @ApiOperation("检测登录状态")
    public Result checkLogin() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null && subject.isAuthenticated())
            return Result.success("已登录");
        else
            return Result.fail("未登录");
    }

    @GetMapping("/unauthorized")
    public Result notRole() {
        return Result.any(ResultCode.UNAUTHORIZED);
    }

    @GetMapping("/unauthenticated")
    public Result notLogin() {
        return Result.any(ResultCode.UNAUTHENTICATED);
    }
}
