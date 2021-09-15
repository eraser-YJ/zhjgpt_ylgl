package com.digitalchina.admin.constant.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum LayerEnum {

	RQTYZ("燃气调压站", "RQTYZ"), //
	RQJ("燃气井", "RQJ"), //
	RLJ("热力井", "RLJ"), //
	JSJ("给水井", "JSJ"), //
	WSJ("污水井", "WSJ"), //
	YSJ("雨水井", "YSJ"), //
	DLJ("电力井", "DLJ"), //
	TXL("通讯井", "TXL"), //
	LJT("垃圾桶", "LJT"), //
	YX("邮箱", "YX"), //
	LMP("路名牌", "LMP"), //
	ZDXF("重点消防", "ZDXF"), //
	JYZ("加油站", "JYZ"), //
	GJZD("公交站点", "GJZD"), //
	TCC("停车场", "TCC"), //
	XRXHD("行人信号灯", "XRXHD"), //
	HLD("红绿灯", "HLD"), //
	JTZSP("交通指示牌", "JTZSP");//

	private String name;

	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private LayerEnum(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static List<Map<String, String>> getAll() {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (LayerEnum em : LayerEnum.values()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", em.getName());
			map.put("code", em.getCode());
			result.add(map);
		}
		return result;
	}

}
