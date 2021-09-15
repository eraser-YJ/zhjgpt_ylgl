package com.digitalchina.modules.constant.enums;

/**
 * <数据来源类型>
 * 
 * @author lihui
 * @since 2019年8月5日
 */
public enum SourceTypeEnum {

	WLW(0, "物联网数据（监测点", "监测点"), //
	HLW(1, "业务系统数据（组件", "组件"); //
//	YWXT(2, "互联网数据"); //

	private Integer code;
	private String desc;
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private SourceTypeEnum(Integer code, String desc, String name) {
		this.code = code;
		this.desc = desc;
		this.name = name;
	}

	public static Integer change(String name) {
		for (SourceTypeEnum em : SourceTypeEnum.values()) {
			if (em.getName().equals(name))
				return em.getCode();
		}
		return null;
	}

}
