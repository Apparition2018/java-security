package com.ljh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ljh
 * created on 2022/2/10 13:32
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * Username:    user
     */
    @GetMapping("hello")
    public String hello() {
        return "hello security";
    }

    @GetMapping("index")
    public String index() {
        return "hello index";
    }
}
