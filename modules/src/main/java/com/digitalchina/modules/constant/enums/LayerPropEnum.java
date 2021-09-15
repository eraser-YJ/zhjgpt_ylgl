package com.digitalchina.modules.constant.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalchina.common.utils.JsonMapper;

/**
 * <图层属性>
 * 
 * @author lihui
 * @since 2020年4月24日
 */
public enum LayerPropEnum {

	JT(1, "静态监测"), POINT(11, "点"), LINE(12, "线"), BAR(13, "面"), DT(2, "动态监测"), CGQ(21, "传感器监测");

	private Integer key;

	private String name;

	public static Map<Integer, String> getAllProp() {
		Map<Integer, String> props = new HashMap<>();
		props.put(LayerPropEnum.POINT.getKey(), LayerPropEnum.POINT.getName());
		props.put(LayerPropEnum.LINE.getKey(), LayerPropEnum.LINE.getName());
		props.put(LayerPropEnum.BAR.getKey(), LayerPropEnum.BAR.getName());
		props.put(LayerPropEnum.CGQ.getKey(), LayerPropEnum.CGQ.getName());
		return props;
	}

	public static List<Map<Integer, String>> getTreeProp() {
		// 这个枚举是不会维护的,偷个懒
		String json = "[{\"title\":\"静态资源\",\"key\":\"1\",\"children\":[{\"title\":\"点\",\"key\":\"11\"},{\"title\":\"线\",\"key\":\"12\"},{\"title\":\"面\",\"key\":\"13\"}]},{\"title\":\"动态监测\",\"key\":\"2\",\"children\":[{\"title\":\"传感器监测\",\"key\":\"21\"}]}]";
		List<Map<Integer, String>> rs = (List<Map<Integer, String>>) JsonMapper.fromJsonString(json, Object.class);
		return rs;
	}

	public static LayerPropEnum getParentProp(Integer key) {
		if (null == key)
			return null;
		switch (key) {
		case 11:
			return LayerPropEnum.JT;
		case 12:
			return LayerPropEnum.JT;
		case 13:
			return LayerPropEnum.JT;
		case 22:
			return LayerPropEnum.DT;
		default:
			return null;
		}
	}

	private LayerPropEnum(Integer key, String name) {
		this.key = key;
		this.name = name;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
