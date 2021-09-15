package com.digitalchina.admin.config.security;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.digitalchina.modules.utils.AuthorizeUtil;

/**
 * 使用redis的方式存储token
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
@Component
public class RedisTokenProvider implements TokenProvider {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Value("${server.token.timeoutInseconds:1800}")
	private long tokenValidityInseconds;

	@Value("${server.token.perfix:admin}")
	private String perfix;

	public RedisTokenProvider() {
		super();
	}

	public RedisTokenProvider(RedisTemplate<String, Object> redisTemplate, long tokenValidityInseconds, String perfix) {
		super();
		this.redisTemplate = redisTemplate;
		this.tokenValidityInseconds = tokenValidityInseconds;
		this.perfix = perfix;
	}

	@Override
	public String createToken(AmUserDetail detail) {
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(createRedisTokenKey(token), detail, tokenValidityInseconds, TimeUnit.SECONDS);
		return token;
	}

	@Override
	public void refreshToken(String token) {
		redisTemplate.expire(createRedisTokenKey(token), tokenValidityInseconds, TimeUnit.SECONDS);
	}

	@Override
	public AmUserDetail getUserDetail(String token) {
		return (AmUserDetail) redisTemplate.opsForValue().get(createRedisTokenKey(token));
	}

	/**
	 * redis key格式
	 *
	 * @param token
	 * @return
	 */
	public String createRedisTokenKey(String token) {
		return AuthorizeUtil.getUserRedisKey(perfix + ":" + token);
	}

	@Override
	public void clearToken(String token) {
		redisTemplate.delete(createRedisTokenKey(token));
	}

	@Override
	public void updateUser(String token, AmUserDetail detail) {
		redisTemplate.opsForValue().set(createRedisTokenKey(token), detail, tokenValidityInseconds, TimeUnit.SECONDS);
	}
}
