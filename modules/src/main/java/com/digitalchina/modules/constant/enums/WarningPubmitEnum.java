package com.digitalchina.modules.constant.enums;

/**
 * <预警发布发送状态>
 * 
 * @author lihui
 * @since 2019年9月12日
 */
public enum WarningPubmitEnum {

	NONE(0), // 未发布未发送
	MSGMIT(1), // 发布
	MSGPUB(2), // 发送
	ALL(3); // 发布且发送

	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	private WarningPubmitEnum(Integer code) {
		this.code = code;
	}

}
