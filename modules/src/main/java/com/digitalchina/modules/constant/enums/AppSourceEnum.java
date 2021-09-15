package com.digitalchina.modules.constant.enums;

/**
 * APP资源类型
 * 
 * @author liuping
 */
public enum AppSourceEnum {

	RunReport(0, "运行报告");

	private Integer code;
	private String desc;

	AppSourceEnum(Integer code, String desc) {
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
