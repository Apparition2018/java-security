package com.mmall.demo2.model;

import java.util.HashSet;
import java.util.Set;

/**
 * User
 *
 * @author Arsenal
 * created on 2019/6/27 1:10
 */
public class User {

    private Integer uid;

    private String username;

    private String password;

    private Set<Role> roles = new HashSet<>();

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPasword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
