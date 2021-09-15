package com.digitalchina.modules.constant.enums;

/**
 * 环境枚举
 * 
 * @author warrior
 *
 * @since 2019年1月30日
 */
public enum ProfileEnum {

	DEV("dev", "开发环境"), TEST("test", "测试环境"), UAT("uat", "UAT环境"), PROD("prod", "生产环境");

	private String code;

	private String name;

	private ProfileEnum(String code, String name) {
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
