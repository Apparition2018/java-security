package com.mmall.demo2.mapper;

import com.mmall.demo2.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 *
 * @author Arsenal
 * created on 2019/6/27 1:15
 */
public interface UserMapper {

    User findByUsername(@Param("username") String username);

}
