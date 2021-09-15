package com.digitalchina.modules.constant.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digitalchina.common.utils.MapUtil;

/**
 * <专业领域>
 * 
 * @author lihui
 * @since 2020年3月31日
 */
public enum MajorEnum {

	GGWSSJ("公共卫生事件类"), ZRZH("自然灾害类"), GGZC("公共政策与管理类"), SGZN("事故灾害类"), SHAQ("社会安全事件类"), DZTX("电子与通讯类");

	private String name;

	private MajorEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<Map<String, Object>> maps() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (MajorEnum em : MajorEnum.values()) {
			result.add(MapUtil.getHashMap("name", em.getName()));
		}
		return result;
	}

}
