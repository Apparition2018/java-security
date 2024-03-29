package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

/**
 * AclParam
 *
 * @author Arsenal
 * created on 2019/07/18 03:49
 */
@Getter
@Setter
@ToString
public class AclParam {
    private Integer id;
    @NotBlank(message = "权限点名称不可以为空")
    @Size(min = 2, max = 20, message = "权限点名称长度需要在2-20个字之间")
    private String name;
    @NotNull(message = "必须指定权限模块")
    private Integer aclModuleId;
    @Size(min = 6, max = 100, message = "权限点URL长度需要在6-100个字符之间")
    private String url;
    @NotNull(message = "必须指定权限点的类型")
    @Min(value = 1, message = "权限点类型不合法")
    @Max(value = 3, message = "权限点类型不合法")
    private Integer type;
    @NotNull(message = "必须指定权限点的状态")
    @Min(value = 0, message = "权限点状态不合法")
    @Max(value = 1, message = "权限点状态不合法")
    private Integer status;
    @NotNull(message = "必须指定权限点的展示顺序")
    private Integer seq;
    @Size(max = 200, message = "权限点备注长度需要在200个字符以内")
    private String remark;
}

