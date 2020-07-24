package com.example.demo.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @类名 : RestConfiguration
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/13 13:36
 * @版本 1.0
 */
@Configuration
public class RestConfiguration {
    @Autowired
    RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        return builder.build();
    }
}
