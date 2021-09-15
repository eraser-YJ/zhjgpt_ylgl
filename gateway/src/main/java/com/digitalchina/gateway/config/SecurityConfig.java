package com.digitalchina.gateway.config;

import com.digitalchina.modules.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 使用redis的方式存储token
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
@Configuration
public class SecurityConfig {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Value("${server.token.timeoutInseconds:1800}")
	private long tokenValidityInseconds;

	@Value("${server.token.perfix:platform}")
	private String perfix;

	@Bean
	public TokenProvider tokenProvider() {
		return new RedisTokenProvider(redisTemplate, tokenValidityInseconds, perfix);
	}

	@Bean
	public UserTokenHolder userTokenHolder() {
		return new RedisUserIdHolder(stringRedisTemplate, tokenValidityInseconds, perfix);
	}

	@Bean
	public Object bootstrap(@Autowired TokenProvider provider) {
		SecurityUtil.setTokenProvider(provider);
		return new Object();
	}

}
