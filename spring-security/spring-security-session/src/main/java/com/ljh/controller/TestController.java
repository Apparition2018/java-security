package com.ljh.controller;

import com.ljh.entity.Users;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ljh
 * created on 2022/2/10 13:32
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 匿名访问
     * Username:    user
     */
    @GetMapping("hello")
    public String hello() {
        return "hello security";
    }

    /**
     * 主页
     */
    @GetMapping("index")
    public String index() {
        return "hello index";
    }

    /**
     * `@Secured
     */
    @GetMapping("update")
    @Secured({"ROLE_sale", "ROLE_manager"})
    public String update() {
        return "hello update";
    }

    /**
     * `@PreAuthorize       方法执行前权限验证
     */
    @GetMapping("update2")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String update2() {
        return "hello update2";
    }

    /**
     * `@PostFilter         集合数组类型返值过滤
     */
    @GetMapping("getAll")
    @PostFilter("filterObject.id == 1")
    public List<Users> getAll() {
        List<Users> usersList = new ArrayList<>();
        usersList.add(new Users(1, "mary", "123"));
        usersList.add(new Users(2, "john", "123"));
        return usersList;
    }
}
