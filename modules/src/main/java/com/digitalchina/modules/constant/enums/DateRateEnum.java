package com.digitalchina.modules.constant.enums;

/**
 * 日期频率枚举
 *
 * @author warrior
 * @since 2019-08-19
 */
public enum DateRateEnum {
	YEAR("year", "年"), MONTH("month", "月"), DAY("day", "日"), HOUR("hour", "时"), MINUTE("minute", "分"), SECOND("second",
			"秒");

	private String code;

	private String name;

	private DateRateEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
