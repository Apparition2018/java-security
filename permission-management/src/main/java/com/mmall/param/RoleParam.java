package com.mmall.param;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RoleParam
 *
 * @author Arsenal
 * created on 2019/7/18 10:48
 */
@Getter
@Setter
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank(message = "角色名称不可以为空")
    @Size(min = 2, max = 20, message = "角色名称长度需要在2-20个字之间")
    private String name;

    @Min(value = 1, message = "角色类型不合法")
    @Max(value = 2, message = "角色类型不合法")
    private Integer type = 1;

    @NotNull(message = "角色状态不可以为空")
    @Min(value = 0, message = "角色状态不合法")
    @Max(value = 1, message = "角色状态不合法")
    private Integer status;

    @Size(max = 200, message = "角色备注长度需要在200个字符以内")
    private String remark;
}