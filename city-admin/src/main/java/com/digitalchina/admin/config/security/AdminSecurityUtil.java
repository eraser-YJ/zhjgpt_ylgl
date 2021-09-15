package com.digitalchina.admin.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 安全工具类
 * 
 * @author Warrior 2019年8月4日
 */
@Component
public class AdminSecurityUtil {

	private static final ThreadLocal<AmUserDetail> userHolder = new InheritableThreadLocal<>();
	private static final ThreadLocal<String> tokenHolder = new InheritableThreadLocal<>();

	private static TokenProvider tp;

	@Autowired
	public void setTokenProvider(TokenProvider tp) {
		AdminSecurityUtil.tp = tp;
	}

	/**
	 * 获取当前登陆用户信息，需要的时候才查询redis
	 *
	 * @return
	 */
	public static AmUserDetail currentUser() {
		if (userHolder.get() != null) {
			return (AmUserDetail) userHolder.get();
		}
		if (tokenHolder.get() != null) {
			AmUserDetail detail = (AmUserDetail) tp.getUserDetail(tokenHolder.get());
			setCurrentUser(detail);
			return detail;
		}

		return null;
	}

	/**
	 * 获取当前登陆用户ID
	 *
	 * @return
	 */
	public static Integer currentUserId() {
		AmUserDetail user = currentUser();
		if (user != null) {
			return user.getId();
		}
		return null;
	}

	/**
	 * 设置当前登陆token
	 *
	 * @param userinfo
	 * @return
	 */
	public static void setCurrentToken(String token) {
		tokenHolder.remove();
		userHolder.remove();
		if (token != null) {
			tokenHolder.set(token);
		}
	}

	/**
	 * 设置当前登陆用户
	 *
	 * @param userinfo
	 * @return
	 */
	private static void setCurrentUser(AmUserDetail userinfo) {
		userHolder.remove();
		if (userinfo != null) {
			userHolder.set(userinfo);
		}
	}
}
