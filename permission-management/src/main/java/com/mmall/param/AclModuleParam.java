package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

/**
 * AclModuleParam
 *
 * @author Arsenal
 * created on 2019/07/18 02:25
 */
@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank(message = "权限模块名称不可以为空")
    @Size(min = 2, max = 20, message = "权限模块名称长度需要在2~20个字之间")
    private String name;

    private Integer parentId = 0;

    @NotNull(message = "权限模块展示顺序不可以为空")
    private Integer seq;

    @NotNull(message = "权限模块状态不可以为空")
    @Min(value = 0, message = "权限模块状态不合法")
    @Max(value = 1, message = "权限模块状态不合法")
    private Integer status;

    @Size(max = 200, message = "权限模块备注需要在200个字之间")
    private String remark;
}
