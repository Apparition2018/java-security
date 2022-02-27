package com.ljh.dao.impl;

import com.ljh.dao.UserDao;
import com.ljh.vo.User;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserDaoImpl
 *
 * @author Arsenal
 * created on 2021/2/23 2:29
 */
@Repository
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByUserName(String userName) {

        String sql = "select username, password from user where username = ?";
        List<User> list = jdbcTemplate.query(sql, (resultSet, i) -> {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }, userName);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<String> queryRolesByUserName(String userName) {
        String sql = "select rname from user u join user_role ur on u.uid = ur.uid join role r on ur.rid = r.rid where username = ?";
        return jdbcTemplate.query(sql, ((resultSet, i) -> resultSet.getString("rname")), userName);
    }
}
