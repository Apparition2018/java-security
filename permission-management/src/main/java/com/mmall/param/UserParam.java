package com.mmall.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * UserParam
 *
 * @author Arsenal
 * created on 2019/7/17 1:42
 */
@Getter
@Setter
public class UserParam {
    private Integer id;
    @NotBlank(message = "用户名不可以为空")
    @Size(min = 1, max = 20, message = "用户名长度需要在20个字以内")
    private String username;
    @NotBlank(message = "电话不可以为空")
    @Size(min = 1, max = 13, message = "电话长度需要在13个字以内")
    private String telephone;
    @NotBlank(message = "邮箱不允许为空")
    @Size(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    private String mail;
    @NotNull(message = "必须提供用户所在的部门")
    private Integer deptId;
    @NotNull(message = "必须指定用户的状态")
    @Min(value = 0, message = "用户状态不合法")
    @Max(value = 2, message = "用户状态不合法")
    private Integer status;
    @Size(max = 200, message = "备注长度需要在200个字以内")
    private String remark = "";
}
