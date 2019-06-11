package com.gdjb.oauth.pojo;

/**
 * @ClassName : User
 * @Description : TODO
 * @Author : gile
 * @Date : 2019/5/16 18:55
 * @Version 1.0
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
