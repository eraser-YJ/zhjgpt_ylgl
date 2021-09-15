package com.digitalchina.modules.constant.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digitalchina.common.utils.MapUtil;

/**
 * <预警报警异常状态枚举>
 * 
 * @author zhaoyan liang
 * @since 2019年8月8日
 */
public enum WnctyEnum {

	FCW(0, "预警"), //
	ALARM(1, "报警"), //
	EXCEPTION(2, "异常"), //
	ELSE(3, "其他");

	private Integer code;

	private String desc;

	WnctyEnum(Integer code, String desc) {
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

	public static List<Map<String, Object>> maps() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (WnctyEnum em : WnctyEnum.values()) {
			list.add(MapUtil.getHashMap("key", em.getCode(), "value", em.getDesc()));
		}
		return list;
	}

	public static WnctyEnum map(Integer code) {
		for (WnctyEnum em : WnctyEnum.values()) {
			if (em.getCode().equals(code))
				return em;
		}
		return WnctyEnum.ELSE;
	}

}
