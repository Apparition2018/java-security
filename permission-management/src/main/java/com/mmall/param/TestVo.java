package com.mmall.param;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TestVo
 *
 * @author Arsenal
 * created on 2019/7/9 2:50
 */
@Setter
@Getter
public class TestVo {

    @NotBlank
    private String msg;

    @NotNull(message = "id 不可以为空")
    @Max(value = 10, message = "id 不能大于10")
    @Min(value = 0, message = "id 至少大于等于0")
    private Integer id;

    private List<String> str;
}
