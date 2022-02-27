package com.ljh.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * User
 *
 * @author Arsenal
 * created on 2019/6/27 1:10
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7972343542294635901L;
    private Integer uid;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
