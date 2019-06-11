package com.gdjb.oauth.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author jie
 * @date 2019/5/26 12:45
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        //principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
        //在经OAuth2拦截后，是OAuth2Authentication
        return principal;
    }

}