package com.gdjb.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author jie
 * @date 2019/5/26 12:10
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService myUserDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
              //  .antMatchers("/static/**","/index.html").hasAnyRole("ROLE_SUPER_ADMIN")
                .antMatchers("/static/js/angularjs/jquery.min.js","/static/js/angularjs/angular.min.js","/static/js/base.js","/static/js/service/testService.js",
                        "/static/js/controller/testController.js","/login.html").permitAll()// 这些页面不需要身份认证,其他请求需要认证
                .anyRequest()   // 任何请求
                .authenticated() // 都需要身份认证
                // .anonymous()
                .and()
                .formLogin()
                .loginPage("/login.html")// 自定义登录页面
                .failureUrl("/login.html")
                .loginProcessingUrl("/control/authentication/login")// 自定义登录路径
                .defaultSuccessUrl("/").permitAll()
                .and()
                //   .logout().logoutSuccessUrl("/control/authentication/login?logout")
                .logout()   //退出登录相关配置
                .logoutUrl("/control/authentication/logout")   //自定义退出登录页面
                //      .logoutSuccessHandler(new CoreqiLogoutSuccessHandler()) //退出成功后要做的操作（如记录日志），和logoutSuccessUrl互斥
                //.logoutSuccessUrl("/index") //退出成功后跳转的页面
              //  .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()// 对请求授权
                // error  127.0.0.1 将您重定向的次数过多
                .and()
                .csrf().disable();// 禁用跨站攻击
    }

 /*   @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest()
                .permitAll();
    }*/

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());//配置自定义DetailService
    }





/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);

        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }*/

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

 /*   @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .csrf().disable();
    }*/

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 默认密码处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 跨域
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**");
            }
        };
    }
}