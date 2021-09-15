package com.digitalchina.modules.constant.enums;

/**
 * 公共附件表主表类型枚举
 * 建议每个系统用一个前缀开头
 * 例如：应急  901,911...
 * 
 * @author liuping
 */
public enum MsttypeEnum {

	/**
	 * 应急系统 900
	 */
	EM_PREVENT(901, "emergency.em_prevent","预处理事件表"),
	EM_EVENT(902, "emergency.em_event","应急事件表"),
	EM_PLAN(903, "emergency.em_plan","应急预案表");

	private Integer code;
	private String table;
	private String desc;

	MsttypeEnum(Integer code, String table, String desc) {
		this.code = code;
		this.table = table;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
