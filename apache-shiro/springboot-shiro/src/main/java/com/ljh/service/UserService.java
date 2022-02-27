package com.ljh.service;

import com.ljh.model.User;

/**
 * UserService
 *
 * @author Arsenal
 * created on 2019/6/27 1:16
 */
public interface UserService {

    User findByUsername(String username);
}
