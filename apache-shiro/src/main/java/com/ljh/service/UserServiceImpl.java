package com.ljh.service;

import com.ljh.mapper.UserMapper;
import com.ljh.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl
 *
 * @author Arsenal
 * created on 2019/6/27 1:18
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
