package com.project.controller;

import com.project.dto.AuthUser;
import com.project.dto.UserInfo;
import com.project.po.UserPO;
import com.project.po.UserRole;
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
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Api(tags = "用户权限")
public class UserController {
    @Autowired
    StringRedisTemplate redisTemplate;
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ApiOperation(value = "普通用户注册")
    public Result register(@RequestBody @Valid AuthUser authUser) {
        if (userService.getByName(authUser.getUsername()) != null)
            return Result.success("注册失败，该用户已存在");
        if (redisTemplate.opsForValue().get(authUser.getUsername()) != null)
            return Result.success("请勿重复申请注册");
        redisTemplate.opsForValue().set(authUser.getUsername(), authUser.getPassword());
        return Result.success("注册申请已提交给管理员");

    }

    @PostMapping("/super-register")
    @ApiOperation(value = "测试用的注册，前端不要用此接口")
    public Result superRegister(@RequestBody @Valid AuthUser authUser) {
        //判断用户名是否已经存在，如果已存在，那就返回错误信息
        if (userService.isNameExist(authUser.getUsername()))
            return Result.fail("注册失败，该用户已存在");
        if (userService.saveAuthUser(authUser)){
            return Result.success("注册成功");
        }
        return Result.fail();
    }


    @GetMapping("/admin/approval")
    @ApiOperation(value = "全部待审批的注册申请")
    public Result approval() {
        Set<String> a = redisTemplate.keys("*");
        System.out.println(a);
        List<AuthUser> ans = new ArrayList<AuthUser>();
        for (String b : a) {
            ans.add(new AuthUser(b, redisTemplate.opsForValue().get(b).toString()));
        }

        Result result = Result.success();
        HashMap<String, Object> ans1 = new HashMap<>();
        ans1.put("users", ans);
        System.out.println(ans1);
        result.setData(ans1);
        return result;
    }


    @PostMapping("/admin/registeruser")
    @ApiOperation(value = "前端返回某一用户的审批结果,pass为1表示通过审批为0反之(写在url上)")
    public Result approval1(@RequestBody AuthUser authuser, @RequestParam String pass) throws Exception {

        redisTemplate.delete(authuser.getUsername());
        if (pass.equals("1")) {
            if (userService.saveAuthUser(authuser))
                return Result.success(authuser.getUsername() + "注册成功");
            else return Result.success("注册失败该用户名已存在");
        } else {

            return Result.success(authuser.getUsername() + "注册失败");
        }
    }


    //shiro加持的登录方法，参数传进来一个UserDTO用户对象
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    public Result login(@RequestBody @Valid AuthUser authUser) {
        String msg;
        UserPO ans = userService.getByName(authUser.getUsername());
        try {
            verifyUser(authUser);
            return new Result(2000, "user", ans);
        } catch (UnknownAccountException e) {
            msg = "用户" + authUser + "登录失败，用户名不存在";
        } catch (IncorrectCredentialsException e) {
            msg = "用户" + authUser + "登录失败，用户名或密码错误";
        } catch (AuthenticationException e) {
            msg = "用户" + authUser + "登录失败，未知原因";
        }
        log.info(msg);

        return new Result(2000, "user", ans);
    }


    private void verifyUser(AuthUser authUser) {
        //获取登录时输入的用户名
        String username = authUser.getUsername();
        String password = authUser.getPassword();
        //把输入的用户名+密码，放在token里面
        UsernamePasswordToken token =
                new UsernamePasswordToken(username, password);
        subject().login(token);
    }

    private Subject subject() {
        return SecurityUtils.getSubject();
    }

    @DeleteMapping("/logoff")
    @ApiOperation(value = "注销当前用户", notes = "注销当前用户，需要处于登录状态")
    public Result logoff() {
        if (!subject().isAuthenticated())
            return Result.fail("尚未登录，无法注销");
        String username = (String) subject().getPrincipal();
        subject().logout();
        userService.removeByName(username);
        log.info("用户{}注销成功", username);
        return Result.success("注销成功");
    }

    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        Subject subject = subject();
        log.info("用户{}退出登录", subject.getPrincipal());
        if (subject.isAuthenticated())
            subject.logout();
        return Result.success("登出成功");
    }

    @GetMapping("check-login")
    @ApiOperation("检测登录状态")
    public Result checkLogin() {
        Subject subject = subject();
        if (subject != null && subject.isAuthenticated())
            return Result.success("已登录");
        else
            return Result.fail("未登录");
    }

    @RequestMapping("/unauthorized")
    public Result notRole() {
        return Result.any(ResultCode.UNAUTHORIZED);
    }

    @RequestMapping("/unauthenticated")
    public Result notLogin() {
        return Result.any(ResultCode.UNAUTHENTICATED);
    }

    @GetMapping("/admin/users")
    @ApiOperation(value = "获取全部注册用户", notes = "仅获取用户名和角色信息，不包括管理员账户")
    public Result getAllUsers() {
        List<UserInfo> userInfos = userService.listUserInfo();
        return Result.success().put("users", userInfos);
    }

    @PostMapping("/admin/user_delete")
    @ApiOperation(value = "删除用户", notes = "需要管理员权限，删除任意指定用户名的普通用户，不可删除管理员")
    public Result deleteUser(@RequestParam String username) {
        System.out.println("delete" + username);
        if (!userService.isNameExist(username)) {
            log.info("用户{}不存在，删除失败", username);
            return Result.fail("该用户不存在或已被删除");
        }
        System.out.println("delete" + username);
        String role = userService.getRoleByName(username);
        System.out.println("delete:" + role);
        System.out.println("delete" + username);
        if (UserRole.ROLE_ADMIN.equals(role)) {
            log.info("用户{}为管理员，无法删除", username);
            return Result.fail("该用户为管理员，无法删除");
        }
        //从数据库删除用户
        userService.removeByName(username);
        log.info("用户{}被管理员删除", username);
        return Result.success("删除用户成功");
    }

    @PostMapping("/admin/user")
    @ApiOperation(value = "管理员创建新用户", notes = "需要管理员权限")
    public Result addUser(@RequestBody @Valid AuthUser authUser) {
        if (userService.saveAuthUser(authUser)) {
            log.info("用户{}创建成功", authUser);
            return Result.success("创建成功");
        }
        log.info("用户{}创建失败，用户名已存在", authUser);
        return Result.success("创建失败，用户名已存在");
    }
}
