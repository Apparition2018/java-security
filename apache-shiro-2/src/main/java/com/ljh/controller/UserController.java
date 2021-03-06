package com.ljh.controller;

import com.ljh.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController
 *
 * @author Arsenal
 * created on 2021/2/7 2:31
 */
@Controller
public class UserController {

    @PostMapping("subLogin")
    @RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
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

    // @RequiresRoles("admin")
    @RequestMapping(value = "/testRoles", method = RequestMethod.GET)
    @ResponseBody
    public String testRoles() {
        return "testRoles success";
    }

    @RequestMapping(value = "/testRoles2", method = RequestMethod.GET)
    @ResponseBody
    public String testRoles2() {
        return "testRoles2 success";
    }

    // @RequiresPermissions("add")
    @RequestMapping(value = "/testPerms", method = RequestMethod.GET)
    @ResponseBody
    public String testPerms() {
        return "testPerms success";
    }

    @RequestMapping(value = "/testPerms2", method = RequestMethod.GET)
    @ResponseBody
    public String testPerms2() {
        return "testPerms2 success";
    }
}
