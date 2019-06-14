package com.example.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class UlegalZCConfiger {



   /* @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbc = new JdbcTemplate();
        jdbc.setDataSource(dataSource());
        return jdbc;
    }*/

   /* @Bean(name = "dataSource")
    @Qualifier(value = "dataSource")
    @Primary
    @ConfigurationProperties(prefix = "c3p0")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();
    }
*/
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




/*

    @Bean
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        template.setAccessTokenProvider(provider);
        return template;
    }
*/

    @Bean("RestTemplate")
    public OAuth2RestOperations restTemplate() {
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
//        OAuth2RestTemplate template = new OAuth2RestTemplate(null, new DefaultOAuth2ClientContext(atr));
        OAuth2RestTemplate template = new OAuth2RestTemplate(resource(), new DefaultOAuth2ClientContext(atr));
        ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
        template.setAccessTokenProvider(provider);
        //template.setAuthenticator(new AuthenticationScheme());
        return template;
    }


    @Bean
    public AuthorizationCodeResourceDetails authorizationCode() {
        AuthorizationCodeResourceDetails resourceDetails = new AuthorizationCodeResourceDetails();
        resourceDetails.setClientId("jie");
        resourceDetails.setClientSecret("jbkj");
        resourceDetails.setAccessTokenUri("http://localhost:8080/oauth/token");
        resourceDetails.setScope(Arrays.asList("app","test"));
        resourceDetails.setPreEstablishedRedirectUri(("http://localhost:8090/"));
        resourceDetails.setGrantType("authorization_code");
        resourceDetails.setUseCurrentUri(true);
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
        return resourceDetails;
    }
/*
    private AuthorizationCodeResourceDetails resource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();

//        List<Map<String,String>> result = configService.getSystemVo(" select * from system_default where pid = ( select id from system_default where key_name = 'defaultSystemAuth' and key_value = '1' ) ORDER BY pid, id; ");
//        Map auth = result.get(0);
        resource.setClientId("jie");
        resource.setClientSecret("jbkj");
        resource.setAccessTokenUri("http://172.20.0.156:8080/oauth/token?code="+ Controller.code);
        resource.setScope(Arrays.asList("app","test"));
        resource.setGrantType("authorization_code");
        return resource;
    }*/
    private ResourceOwnerPasswordResourceDetails resource() {
        ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
   //     List<Map<String,String>> result = configService.getSystemVo(" select * from system_default where pid = ( select id from system_default where key_name = 'defaultSystemAuth' and key_value = '1' ) ORDER BY pid, id; ");
//        Map auth = result.get(0);
        resource.setClientId("jie");
        resource.setClientSecret("jbkj");
        resource.setAccessTokenUri("http://172.20.0.156:8080/oauth/token");
        resource.setScope(Arrays.asList("app","test"));
        resource.setUsername("root");
        resource.setPassword("root");
        resource.setGrantType("password");
        return resource;
    }

}
