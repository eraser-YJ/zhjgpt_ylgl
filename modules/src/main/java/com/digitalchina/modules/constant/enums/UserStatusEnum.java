package com.digitalchina.modules.constant.enums;

/**
 * 用户状态枚举
 * 
 * @author warrior
 *
 * @since 2019年1月7日
 */
public enum UserStatusEnum {

	ACTIVE("0", "正常"), FREEZE("1", "冻结");

	UserStatusEnum(String code, String name) {
		this.code = code;
		this.msg = name;
	}

	private String code;

	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
