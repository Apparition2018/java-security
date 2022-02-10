package com.mmall.beans;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * PageQuery
 *
 * @author Arsenal
 * created on 2019/07/17 23:51
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}

