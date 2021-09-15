package com.digitalchina.modules.constant.enums;

/**
 * 应急系统应急事件状态
 * 
 * @author liuping
 */
public enum EvtstEnum {

	DISPOSING(1, "处置中"),
	AFTERMATH(2, "善后中"),
	CLOSED(3, "已关闭");

	private Integer code;
	private String desc;

	EvtstEnum(Integer code, String desc) {
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
