package com.digitalchina.modules.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志结果枚举
 * 
 * @author warrior
 *
 * @since 2019年3月2日
 */
public enum LogResEnum {
	SUCCESS(1,"操作成功"), FAIL(0,"操作失败");

	LogResEnum(Integer code,String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static List<Map<String, Object>> toList() {
		List<Map<String, Object>> list = new ArrayList<>();
		for (LogResEnum item : LogResEnum.values()) {
			Map<String, Object> temp = new HashMap<>();
			temp.put("id", item.name());
			temp.put("code",item.code);
			temp.put("name", item.msg);
			list.add(temp);
		}
		return list;
	}

}
