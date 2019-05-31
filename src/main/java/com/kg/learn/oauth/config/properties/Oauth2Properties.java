package com.kg.learn.oauth.config.properties;

/**
 * @author jie
 * @date 2019/5/26 20:47
 */
public class Oauth2Properties {

    private String tokenStore;
    private String jwtSigningKey;

    public String getTokenStore() {
        return tokenStore;
    }

    public void setTokenStore(String tokenStore) {
        this.tokenStore = tokenStore;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }
}