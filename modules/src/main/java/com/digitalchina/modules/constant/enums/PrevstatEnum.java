package com.digitalchina.modules.constant.enums;

/**
 * 应急系统预处理事件状态
 * 
 * @author liuping
 */
public enum PrevstatEnum {

	PENDING(0, "等待处理"),
	ACCEPTED(1, "已接受"),
	REFUSED(-1, "拒绝");

	private Integer code;
	private String desc;

	PrevstatEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
