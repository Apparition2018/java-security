package com.ljh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Users
 *
 * @author ljh
 * created on 2022/2/10 15:22
 */
@Data
@AllArgsConstructor
public class Users {
    private Integer id;
    private String username;
    private String password;
}
