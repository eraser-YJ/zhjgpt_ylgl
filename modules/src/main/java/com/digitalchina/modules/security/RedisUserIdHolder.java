package com.digitalchina.modules.security;

import cn.hutool.core.util.StrUtil;
import com.digitalchina.modules.utils.AuthorizeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 使用redis的方式存储token
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
public class RedisUserIdHolder implements UserTokenHolder {

	private StringRedisTemplate redisTemplate;

	private long tokenValidityInseconds;

	private String perfix;

	public RedisUserIdHolder() {
		super();
	}

	public RedisUserIdHolder(StringRedisTemplate redisTemplate, long tokenValidityInseconds, String perfix) {
		super();
		this.redisTemplate = redisTemplate;
		this.tokenValidityInseconds = tokenValidityInseconds;
		this.perfix = perfix;
	}

	/**
	 * redis key格式
	 * @param app
	 * @param userId
	 * @return
	 */
	public String createRedisTokenKey(String app,String userId) {
		 app = StrUtil.isBlank(app) ? perfix : app;
		return AuthorizeUtil.getUserTokenRedisKey(app,userId);
	}

	@Override
	public void saveUserToken(String app, String userId, String token) {
		redisTemplate.opsForValue().set(createRedisTokenKey(app,userId), token, tokenValidityInseconds, TimeUnit.SECONDS);
	}

	@Override
	public void refreshUserToken(String app, String userId) {
		redisTemplate.expire(createRedisTokenKey(app,userId), tokenValidityInseconds, TimeUnit.SECONDS);
	}

	@Override
	public String getUserToken(String app, String userId) {
		return  redisTemplate.opsForValue().get(createRedisTokenKey(app,userId));
	}

	@Override
	public void clearUserToken(String app, String userId) {
		redisTemplate.delete(createRedisTokenKey(app,userId));
	}

	@Override
	public void updateUserToken(String app, String userId, String token) {
		redisTemplate.opsForValue().set(createRedisTokenKey(app,userId), token, tokenValidityInseconds, TimeUnit.SECONDS);
	}
}
