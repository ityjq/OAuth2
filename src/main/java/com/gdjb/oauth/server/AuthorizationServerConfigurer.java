package com.gdjb.oauth.server;

import com.gdjb.oauth.pojo.oauth.ThirdClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jie
 * @date 2019/5/26 12:29
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Bean // 声明TokenStore实现
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService myUserDetailsService;
/*
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    private TokenEnhancer jwtTokenEnhancer;*/

    @Autowired
    private ThirdClientService thirdClientService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        List<ThirdClient> all = thirdClientService.findAll();

        ThirdClient jbkj1 = all.get(0);
        ThirdClient jbkj2 = all.get(1);
        ThirdClient jbkj3 = all.get(2);

        //添加客户端信息
        //使用内存存储OAuth客户端信息
        clients.jdbc(dataSource);
       /* clients.inMemory()
                // client_id
                .withClient(jbkj1.getclientId())
                // client_secret
                .secret(passwordEncoder.encode(jbkj1.getSecret()))
                // 该client允许的授权类型，不同的类型，则获得token的方式不一样。
                .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
//                .authorizedGrantTypes("authorization_code")
                .resourceIds(jbkj1.getResourceIds())
                //回调uri，在authorization_code与implicit授权方式时，用以接收服务器的返回信息
                .redirectUris(jbkj1.getRedirectUris())
//                .redirectUris("")
                // 允许的授权范围
                .scopes(jbkj1.getScopes().split(","))
                // 自动审核：跳过手动选择允许的授权范围
                .autoApprove(true)
                .and()

                .withClient(jbkj2.getclientId())
                .secret(passwordEncoder.encode(jbkj2.getSecret()))
                .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
                .resourceIds(jbkj2.getResourceIds())
                .redirectUris(jbkj2.getRedirectUris())
                .scopes(jbkj2.getScopes().split(","))
                .autoApprove(true)
                .and()

                .withClient(jbkj3.getclientId())
                .secret(passwordEncoder.encode(jbkj3.getSecret()))
                .authorizedGrantTypes("authorization_code", "implicit", "password", "client_credentials", "refresh_token")
                .resourceIds(jbkj3.getResourceIds())
                .redirectUris(jbkj3.getRedirectUris())
                .scopes(jbkj3.getScopes().split(","))
                .autoApprove(true);


        System.out.println(Arrays.toString(jbkj3.getScopes().split(",")) + ":::::::::" + jbkj3.getAuthorizedGrantTypes());
*/
    }


    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true); // support refresh token
        tokenServices.setTokenStore(tokenStore); // use jdbc token store
        return tokenServices;
    }
/*
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //reuseRefreshTokens设置为false时，每次通过refresh_token获得access_token时，也会刷新refresh_token；也就是说，会返回全新的access_token与refresh_token。
        //默认值是true，只返回新的access_token，refresh_token不变。
        endpoints.tokenStore(tokenStore)
                // 设置后 授权码模式 用户可以选取scopes
                .approvalStore(approvalStore)
                .reuseRefreshTokens(true)
                .authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService);

        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);
            enhancers.add(jwtAccessTokenConverter);
            enhancerChain.setTokenEnhancers(enhancers);
            //  System.err.println(jwtAccessTokenConverter+"::::::::::::"+jwtTokenEnhancer);
            endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
        }
    }*/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override // 配置框架应用上述实现
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore).reuseRefreshTokens(true).approvalStore(approvalStore);
        endpoints.authorizationCodeServices(authorizationCodeServices());
        endpoints.userDetailsService(myUserDetailsService);
//        System.err.println("++++++++++" + passwordEncoder.encode("jbkj"));
//         配置TokenServices参数
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);


    }

    @Bean
    public ApprovalStore approvalStore() throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

}