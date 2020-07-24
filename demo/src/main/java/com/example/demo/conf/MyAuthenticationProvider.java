package com.example.demo.conf;


import com.example.demo.pojo.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {


    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("=-=-=-=-=:" + username);

//        List<Map<String,String>> result = configService.getSystemVo(" select * from system_default where pid = ( select id from system_default where key_name = 'defaultSystemUser' and key_value = '1' ) ORDER BY pid, id; ");
//        Map user = result.get(0);
//        if(!user.get("username").equals(username)){
//            throw new BadCredentialsException("Username not found.");
//        }
//
//        //加密过程在这里体现
//        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
//        String encodedPassword = user.get("password").toString();
//        if (!encoder.matches(password, encodedPassword)) {
//            throw new BadCredentialsException("Wrong password.");
//        }
        User user = new User();


        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}