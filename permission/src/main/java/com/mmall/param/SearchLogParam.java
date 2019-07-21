package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * SearchLogParam
 *
 * @author Arsenal
 * created on 2019/7/21 18:24
 */
@Getter
@Setter
@ToString
// 可以用 validator ?
public class SearchLogParam {

    private Integer type;   // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private String fromTime; // yyyy-MM-dd HH:mm:ss

    private String toTime;
}
