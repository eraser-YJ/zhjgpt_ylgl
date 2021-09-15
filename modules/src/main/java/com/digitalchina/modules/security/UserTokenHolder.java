package com.digitalchina.modules.security;

/**
 * UserToken 接口
 * 
 * @author warrior 2018年5月24日
 */
public interface UserTokenHolder {

	/**
	 * 创建存储指定系统的登录用户的token
	 * @param app 系统标志
	 * @param userId 用户id
	 * @param token 需要存储的登录token
	 * @return
	 */
	void saveUserToken(String app,String userId,String token);

	/**
	 * 刷新token过期时间
	 * @param app
	 * @param userId
	 */
	void refreshUserToken(String app,String userId);

	/**
	 * 获取指定登录用户的token
	 * @param app
	 * @param userId
	 * @return
	 */
	String getUserToken(String app,String userId);

	/**
	 * 根据token清除登录用户的token
	 * @param app
	 * @param userId
	 */
	void clearUserToken(String app,String userId);

	/**
	 *更新登录用户的token
	 * @param app
	 * @param userId
	 * @param token
	 */
	void updateUserToken(String app,String userId,String token);
}
