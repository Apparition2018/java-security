package com.ljh.mapper;

import com.ljh.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * UserMapper
 *
 * @author Arsenal
 * created on 2019/6/27 1:15
 */
public interface UserMapper {

    User findByUsername(@Param("username") String username);

}
