package com.digitalchina.modules.constant.enums;

public enum AppMatterLyEnum {
	YJ(0, "预警"), BJ(1, "报警"), YC(2, "异常"), RW(3, "任务");

	private Integer code;

	private String desc;

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

	private AppMatterLyEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
