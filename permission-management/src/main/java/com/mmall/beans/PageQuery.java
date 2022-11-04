package com.mmall.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * PageQuery
 *
 * @author Arsenal
 * created on 2019/07/17 23:51
 */
@Getter
@Setter
public class PageQuery {
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;
    private int offset;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}

