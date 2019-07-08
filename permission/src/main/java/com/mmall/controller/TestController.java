package com.mmall.controller;

import com.mmall.common.JsonData;
import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import com.mmall.param.TestVo;
import com.mmall.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * TestController
 *
 * @author Arsenal
 * created on 2019/7/9 0:17
 */
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello() {
        log.info("hello");
//        throw new RuntimeException("test exception"); // msg: System error
        throw new PermissionException("test exception"); // msg: test exception
//        return JsonData.success("hello, permission"); // msg: hello, permission
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVo vo) throws ParamException {
        log.info("validate");
        BeanValidator.check(vo);
//        try {
//            Map<String, String> map = BeanValidator.validateObject(vo);
//            if (MapUtils.isNotEmpty(map)) {
//                for (Map.Entry<String, String> entry: map.entrySet()) {
//                    log.info("{}->{}", entry.getKey(), entry.getValue());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return JsonData.success("test validate");
    }
}
