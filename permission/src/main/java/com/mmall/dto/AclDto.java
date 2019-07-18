package com.mmall.dto;

import com.mmall.model.SysAcl;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

/**
 * AclDto
 *
 * 显示所有权限，且显示是否有权限操作
 * 而不是只显示有权限操作的权限
 *
 * 好处是管理员可以知道自己是拥有所有权限的操作权限
 *
 * @author Arsenal
 * created on 2019/7/18 12:11
 */
@Getter
@Setter
@ToString
public class AclDto extends SysAcl {

    // 是否要默认选中
    private boolean checked = false;

    // 是否有权限操作
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl acl) {
        AclDto dto = new AclDto();
        BeanUtils.copyProperties(acl, dto);
        return dto;
    }
}