package com.mmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * SearchLogDto
 *
 * @author Arsenal
 * created on 2019/7/21 18:30
 */
@Getter
@Setter
@ToString
public class SearchLogDto {

    private Integer type;   // LogType

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    private Date fromTime;  // yyyy-MM-dd HH:mm:ss

    private Date toTime;    // yyyy-MM-dd HH:mm:ss
}
