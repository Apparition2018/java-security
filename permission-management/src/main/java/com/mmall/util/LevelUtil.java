package com.mmall.util;


import org.apache.commons.lang3.StringUtils;

/**
 * LevelUtil
 *
 * @author Arsenal
 * created on 2019/7/10 3:44
 */
public class LevelUtil {

    private static final String SEPARATOR = ".";

    public static final String ROOT = "0";

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4
    public static String calculateLevel(String parentLevel, int parentId) {
        // 如果父层级为空，则为首层
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }

    }
}
