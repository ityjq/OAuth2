package com.gdjb.oauth.pojo.oauth;

/**
 * @类名 : ThirdClient
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/14 9:45
 * @版本 1.0
 */
public class ThirdClient {

    private String clientId;
    private String secret;
    private String resourceIds;
    private String redirectUris;
    private String scopes;
    private String authorizedGrantTypes;

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }


    public String getclientId() {
        return clientId;
    }

    public void setclientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }
}
