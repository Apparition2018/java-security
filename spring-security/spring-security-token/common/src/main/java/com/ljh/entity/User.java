package com.ljh.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * User
 *
 * @author ljh
 * created on 2022/3/3 1:07
 */
@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {
    private static final long serialVersionUID = 9124638134166619025L;
    @ApiModelProperty(value = "微信openid")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "用户头像")
    private String salt;
    @ApiModelProperty(value = "用户签名")
    private String token;
}
