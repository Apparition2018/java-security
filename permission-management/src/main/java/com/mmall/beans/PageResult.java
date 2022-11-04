package com.mmall.beans;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * PageResult
 *
 * @author Arsenal
 * created on 2019/07/17 23:53
 */
@Getter
@Setter
@ToString
@Builder
public class PageResult<T> {
    private List<T> data;
    private int total;
}
