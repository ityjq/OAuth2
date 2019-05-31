package com.kg.learn.oauth.config.properties;

/**
 * @author jie
 * @date 2019/5/26 20:47
 */
public class SecurityProperties {

    private Oauth2Properties oauth2;

    public Oauth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(Oauth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}