package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName : index
 * @Description : TODO
 * @Author : gile
 * @Date : 2019/5/31 13:19
 * @Version 1.0
 */
public class Index {
    @RequestMapping("/")
    public String index() {
        return "index";
    }


}
