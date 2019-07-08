package com.mmall.demo2.service;

import com.mmall.demo2.model.User;

/**
 * UserService
 *
 * @author Arsenal
 * created on 2019/6/27 1:16
 */
public interface UserService {

    User findByUsername(String username);
}
