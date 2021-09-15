package com.digitalchina.modules.constant.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.digitalchina.common.utils.MapUtil;

/**
 * <警告状态枚举>
 * 
 * @author lihui
 * @since 2019年8月1日
 */
public enum WarningStateEnum {

	DIDNOTSEE(0, "未查看"), //
	HAVETOSEE(1, "已查看"), //
	CLOSE(2, "关闭"), //
	CANCEL(3, "取消"), //
	NB_IN_HAND(11, "内部处理中"), //
	NB_HAND_END(12, "内部已处理"), //
	WB_IN_HAND(21, "外部处理中"), //
	WB_HAND_END(22, "外部已处理"),

	UN_HAND(0, "未处置"), //
	IN_HAND(1, "处置中"), //
	HAND_END(2, "已处置");

	public static Integer mapping(Integer wnstat) {
		// 未处理
		if (wnstat.equals(DIDNOTSEE.getCode()) || wnstat.equals(HAVETOSEE.getCode()))
			return UN_HAND.getCode();

		// 处理中
		if (wnstat.equals(NB_IN_HAND.getCode()) || wnstat.equals(WB_IN_HAND.getCode()))
			return IN_HAND.getCode();

		// 已处理
		if (wnstat.equals(CLOSE.getCode()) || wnstat.equals(CANCEL.getCode()) || wnstat.equals(NB_HAND_END.getCode())
				|| wnstat.equals(WB_HAND_END.getCode()))
			return HAND_END.getCode();

		// 找不到就未处理
		return UN_HAND.getCode();
	}

	public static Integer[] mappings(Integer wnstat) {
		if (null == wnstat)
			return null;
		if (wnstat.equals(UN_HAND.getCode())) {
			return new Integer[] { DIDNOTSEE.getCode(), HAVETOSEE.getCode() };
		}
		if (wnstat.equals(IN_HAND.getCode())) {
			return new Integer[] { NB_IN_HAND.getCode(), WB_IN_HAND.getCode() };
		}
		if (wnstat.equals(HAND_END.getCode())) {
			return new Integer[] { CLOSE.getCode(), CANCEL.getCode(), NB_HAND_END.getCode(), WB_HAND_END.getCode() };
		}

		return null;
	}

	private Integer code;

	private String desc;

	private WarningStateEnum(Integer code, String desc) {
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

	public static List<Map<String, Object>> simpleMap() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list.add(MapUtil.getHashMap("key", UN_HAND.getCode(), "value", UN_HAND.getDesc()));
		list.add(MapUtil.getHashMap("key", IN_HAND.getCode(), "value", IN_HAND.getDesc()));
		list.add(MapUtil.getHashMap("key", HAND_END.getCode(), "value", HAND_END.getDesc()));
		return list;
	}

}
