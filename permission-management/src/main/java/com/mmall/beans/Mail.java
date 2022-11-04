package com.mmall.beans;

import lombok.*;

import java.util.Set;

/**
 * Mail
 *
 * @author Arsenal
 * created on 2019/07/18 00:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String subject;
    private String message;
    private Set<String> receivers;
}

