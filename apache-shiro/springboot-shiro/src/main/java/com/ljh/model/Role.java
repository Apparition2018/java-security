package com.ljh.model;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Role
 *
 * @author Arsenal
 * created on 2019/6/27 1:11
 */
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = -5818657568233370945L;
    private String rid;
    private String rname;
    private Set<Permission> permissions = new HashSet<>();
    private Set<User> users = new HashSet<>();
}
