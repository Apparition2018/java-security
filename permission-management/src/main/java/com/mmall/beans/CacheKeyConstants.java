package com.mmall.beans;

import lombok.Getter;

/**
 * CacheKeyConstants
 *
 * @author Arsenal
 * created on 2019/7/20 2:47
 */
@Getter
public enum CacheKeyConstants {
    SYSTEM_ACLS, // 系统所有权限
    USER_ACLS;  // 用户权限
}
