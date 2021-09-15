package com.digitalchina.modules.constant.enums;

/**
 * <事件来源枚举>
 * 需要注意的是，这个来源可能会被基础管理系统修改！！
 *
 * @author lcl
 * @since 2019年11月15日
 */
public enum BeFromEnum {

	CITY_WARN(1, "监测预警"),
	CITY_ZENS(3, "市民互动");

	private Integer code;

	private String desc;

	BeFromEnum(Integer code, String desc) {
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
