package com.digitalchina.gateway.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@Component
@ConfigurationProperties(prefix = "sso")
public class SsoProperties {

	/**
	 * 获取用户信息地址
	 */
	private String userInfoUrl;

	/**
	 * 登录地址
	 */
	private String loginUrl;
	
	/**
	 * 退出登录地址
	 */
	private String logoutUrl;

	/**
	 * 长春门户地址
	 */
	private String portalUrl;

	/**
	 *使用用户密码授权登录地址
	 */
	private String authLoginUrl;

	/**
	 *修改密码地址
	 */
	private String upPwdUrl;
	/**
	 * 获取用户信息的url
	 * @param token
	 * @return 用户信息的url
	 */
	public String createInfoUrl(String token) {
		return userInfoUrl + token;
	}
	
	/**
	 * 获取退出登录的url
	 * @return 退出登录的url
	 * @throws UnsupportedEncodingException 
	 */
	public String createLoginOutUrl() throws UnsupportedEncodingException {
		return logoutUrl + URLEncoder.encode(portalUrl, "UTF-8");
	}

}
