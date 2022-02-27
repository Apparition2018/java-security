package com.ljh.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Permission
 *
 * @author Arsenal
 * created on 2019/6/27 1:11
 */
@Data
public class Permission implements Serializable {
    private static final long serialVersionUID = 6003895096145854827L;
    private Integer pid;
    private String name;
    private String url;
}
