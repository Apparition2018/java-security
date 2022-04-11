package com.ljh.controller;

import com.ljh.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author Arsenal
 * created on 2021/2/7 2:31
 */
@RestController
public class UserController {

    @PostMapping("subLogin")
    public String subLogin(User user) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            token.setRememberMe(user.isRememberMe());
            subject.login(token);
        } catch (AuthenticationException e) {
            return e.getMessage();
        }
        if (subject.hasRole("admin")) {
            System.out.println("有 admin 权限");
        }
        return "登录成功";
    }

    @RequestMapping("/logout")
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
    }

    @RequiresRoles("admin")
    @GetMapping("/testRoles")
    public String testRoles() {
        return "testRoles success";
    }

    // 在 spring.xml 配置了 /testRoles2 = rolesOr["admin", "admin2"]
    @GetMapping("/testRoles2")
    public String testRoles2() {
        return "testRoles2 success";
    }

    @RequiresPermissions("add")
    @GetMapping("/testPerms")
    public String testPerms() {
        return "testPerms success";
    }

    // 在 spring.xml 配置了 /testPerms2 = perms["delete", "update"]
    @GetMapping("/testPerms2")
    public String testPerms2() {
        return "testPerms2 success";
    }
}
