package com.digitalchina.modules.security;

/**
 * token 接口
 * 
 * @author warrior 2018年5月24日
 */
public interface TokenProvider {

	/**
	 * 创建tonken
	 *
	 * @param detail
	 * @return
	 */
	String createToken(UserDetail detail);

	/**
	 * 刷新token过期时间
	 *
	 * @param token
	 * @return
	 */
	void refreshToken(String token);

	/**
	 * 根据token获取用户信息
	 *
	 * @param token
	 * @return
	 */
	UserDetail getUserDetail(String token);

	/**
	 * 根据token清除登录信息
	 * 
	 * @param token
	 */
	void clearToken(String token);

	/**
	 * 更新用户信息
	 * 
	 * @param token
	 * @param detail
	 */
	void updateUser(String token, UserDetail detail);
}
