package com.digitalchina.modules.constant.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digitalchina.common.utils.MapUtil;

/**
 * <频率枚举>
 * 
 * @author lihui
 * @since 2020年1月15日
 */
public enum FrequencyEnum {

	thrice(0, "最近三次"), five(1, "最近五次"), sept(2, "最近七次"), week(3, "最近一周"), month(4, "最近一个月"), year(5, "最近一年"),
	all(10, "所有");

	private Integer code;

	private String desc;

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

	FrequencyEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static List<Map<String, Object>> fields() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		for (FrequencyEnum em : FrequencyEnum.values()) {
			result.add(MapUtil.getHashMap("id", em.code, "name", em.desc));
		}
		return result;
	}
}
