package com.gdjb.oauth.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jie
 * @date 2019/5/26 19:51
 */
@Component
@ConfigurationProperties(prefix = "custom")
public class ApplicationProperties {

    private ApiProperties api;
    private SecurityProperties security;

    public ApiProperties getApi() {
        return api;
    }

    public void setApi(ApiProperties api) {
        this.api = api;
    }

    public SecurityProperties getSecurity() {
        return security;
    }

    public void setSecurity(SecurityProperties security) {
        this.security = security;
    }

}