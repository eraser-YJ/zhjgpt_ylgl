package com.digitalchina.modules.constant.enums;

/**
 * 公共附件类型
 * 
 * @author liuping
 */
public enum EctypeEnum {

	IMG(1, "img"),
	VEDIO(2, "vedio"),
	PDF(3, "pdf"),
	WORD(4, "word");

	private Integer code;
	private String desc;

	EctypeEnum(Integer code, String desc) {
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
