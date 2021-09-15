package com.digitalchina.modules.exception;

import com.digitalchina.modules.constant.SecurityConstant;

/**
 * 安全异常封装
 * 
 * @author warrior
 *
 * @Date 2018年11月30日
 */
public class SysSecurityException extends RuntimeException {

	private static final long serialVersionUID = 2439358964127843952L;

	private Integer code = SecurityConstant.UNAUTHORIZED;

	private Integer userId;

	public SysSecurityException(String msg) {
		super(msg);
	}

	public SysSecurityException(String msg, Integer userId) {
		super(msg);
		this.userId = userId;
	}

	public SysSecurityException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SysSecurityException(Integer code, String msg) {
		super(msg);
		this.code = code;
	}

	public SysSecurityException(Integer code, String msg, Throwable cause) {
		super(msg, cause);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
