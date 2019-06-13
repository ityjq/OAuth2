package com.gdjb.oauth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : index
 * @Description : 首页展示
 * @Author : gile
 * @Date : 2019/4/13 5:16
 * @Version 1.0
 */
@RestController
public class Index {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/tset1")
    public String test1() {
        return "::::::::尼大爷::::::::";
    }
}
