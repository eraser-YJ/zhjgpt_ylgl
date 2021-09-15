package com.digitalchina.modules.constant.enums;

/**
 * 任务类型枚举
 */
public enum TaskTypeEnum {

	REALTIME(0, "实时"),
	SUMMARY(1, "汇总");

	private Integer code;

	private String desc;

	private TaskTypeEnum(Integer code, String desc) {
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
