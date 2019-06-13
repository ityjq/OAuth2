/**
 *
 */
package com.gdjb.oauth.security;

import com.gdjb.oauth.dao.UserDao;
import com.gdjb.oauth.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jie
 * @date 2019/5/26 13:41
 **/
@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserDao repository;

    public MyUserDetailsService(UserDao userRepository) {
        this.repository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findUserByUsername(username);

     /*   BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(user.getPassword());
        System.err.println("密码:" + encode);*/

        if (user == null)
            throw new UsernameNotFoundException("找不到该账户信息！");          //抛出异常，会根据配置跳到登录失败页面

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();      //GrantedAuthority是security提供的权限类，

        getRoles(user, list);              //获取角色，放到list里面

        org.springframework.security.core.userdetails.User auth_user = null;      //返回包括权限角色的User给security
        try {
            auth_user = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), list);//AuthorityUtils.commaSeparatedStringToAuthorityList("MEMBER")
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth_user;
    }

    /**
     * 获取所属角色
     *
     * @param user
     * @param list
     */
    public void getRoles(User user, List<GrantedAuthority> list) {
        for (String role : user.getRoles().split(",")) {
            //权限如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限，例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限
            list.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
    }


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;


}
