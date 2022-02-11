package com.ljh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController
 *
 * @author ljh
 * created on 2022/2/11 16:51
 */
@Controller
public class LoginController {
    
    @GetMapping("/iLogin")
    public String login() {
        return "login";
    }
}
