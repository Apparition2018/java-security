package com.ljh.dao;

import com.ljh.vo.User;

import java.util.List;
import java.util.Set;

/**
 * UserDao
 *
 * @author Arsenal
 * created on 2021/2/23 2:28
 */
public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUserName(String userName);
}
