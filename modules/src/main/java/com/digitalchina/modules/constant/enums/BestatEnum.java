package com.digitalchina.modules.constant.enums;

/**
 * <事件状态枚举>
 * 
 * @author zhaoyan liang
 * @since 2019年8月8日
 */
public enum BestatEnum {

	PENDING(0, "待处理"),
	NB_STAIR(1, "内部处理中(一级分拨)"),
	NB_ACCESS(2, "内部处理中(二级分拨)"),
	NB_BUSINESS(10, "内部处理中(业务部门)"),
	IN_HAND(20, "应急处理中"),
	HC_STAIR(100, "核查中(一级分拨)"),
	HC_ACCESS(101, "核查中(二级分拨)"),
	CLOSE(1000, "关闭（办结）");

	private Integer code;

	private String desc;

	BestatEnum(Integer code, String desc) {
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
