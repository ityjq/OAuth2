/**
 * 
 */
package com.gdjb.oauth.config;

import com.gdjb.oauth.config.properties.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Token Store
 * Token 存储方式
 *
 * @author jie
 * @date 2019/5/26 20:34
 **/
@Configuration
public class TokenStoreConfig {
	@Autowired
	private TokenStore tokenStore;

	@Bean
	public ApprovalStore approvalStore() throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

	/**
	 * 使用InMemory存储token的配置，只有在custom.security.oauth2.tokenStore配置为inMemory时生效
	 **/
	@Configuration
	@ConditionalOnProperty(prefix = "custom.security.oauth2", name = "tokenStore", havingValue = "inMemory")
	public static class InMemoryTokenStoreConfig {

		@Bean
		public TokenStore tokenStore() {
			//token保存在内存中（也可以保存在数据库、Redis中）。
			//如果保存在中间件（数据库、Redis），那么资源服务器与认证服务器可以不在同一个工程中。
			//注意：如果不保存access_token，则没法通过access_token取得用户信息

			return new InMemoryTokenStore();
		}

	}
	
	/**
	 * 使用redis存储token的配置，只有在custom.security.oauth2.tokenStore配置为redis时生效
	 **/

	@Configuration
	@ConditionalOnProperty(prefix = "custom.security.oauth2", name = "tokenStore", havingValue = "redis")
	public static class RedisTokenStoreConfig {
		
		@Autowired
		private RedisConnectionFactory redisConnectionFactory;
		
		@Bean
		public TokenStore redisTokenStore() {
			return new RedisTokenStore(redisConnectionFactory);
		}
		
	}

	/**
	 * 使用jwt存储token的配置，只有在custom.security.oauth2.tokenStore配置为jwt时生效
	 **/
	@Configuration
	@ConditionalOnProperty(prefix = "custom.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
	public static class JwtTokenStoreConfig {
		
		@Autowired
		private ApplicationProperties applicationProperties;
		
		@Bean
		public TokenStore jwtTokenStore() {
			return new JwtTokenStore(jwtAccessTokenConverter());
		}
		
		@Bean
		public JwtAccessTokenConverter jwtAccessTokenConverter(){
			JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	        converter.setSigningKey(applicationProperties.getSecurity().getOauth2().getJwtSigningKey());
	        return converter;
		}
		
		@Bean
		@ConditionalOnBean(TokenEnhancer.class)
		public TokenEnhancer jwtTokenEnhancer(){
			return new JwtTokenEnhancer();
		}
		
	}

}
