package com.digitalchina.modules.constant.enums;

import java.util.List;

import cn.hutool.core.collection.CollUtil;

public enum EventEnum {

	NORMAL_EVENT("普通事件流程", "normalEventProcess"), COOP_EVENT("协查事件流程", "coopEventProcess"),

	FIRST_AUDIT_COOP(20, "一级审批协查"), FIRST_REFUSE_COOP(21, "一级驳回协查"), SECOND_AUDIT_COOP(10, "二级审批协查"),
	SECOND_REFUSE_COOP(11, "二级驳回协查"), MODIFY_COOP(0, "修改协查申请"), CANCEL_COOP(1, "关闭（取消）"), HANDLE_COOP(30, "处理协查申请"),
	FINISH_COOP(40, "关闭"),

	UNHANDLE("未处理", "未处理"), ALLOCATING("转派中", "派遣中"), HANDLING("处理中", "处理中"), UNCHECKED("待核查", "待核查"),
	UNAUDIT("待审批", "待审批"), REFUSE("拒绝件", "拒绝件"), CANCEL("取消件", "已取消"), UPGRADE("升级件", "升级事件"), TURNBACK("被驳回", "被驳回"),
	RECHECK("复查件", "复查件"), COOPERATE("协查件", "协查件"), FINISH("完成件", "已关闭"),

	// 流程刚创建
	UNHANDLE_STAT(0, "未处理"),
	// 流程创建，点击转派，转派到下级部门，且业务部门未受理前，
	FIRST_LEVEL_ALLOCATING_STAT(1, "一级事件分拨"),
	// 流程创建，转派到下级部门，且下级部门未受理前，
	SECOND_LEVEL_ALLOCATING_STAT(2, "二级事件分拨"), SECOND_REFUSE_STAT(4, "二级退件"),
	// 业务部门受理
	HANDLING_STAT(10, "业务部门确认受理"), SERVICE_REFUSE_STAT(11, "业务部门退件"), EMERGENCY_STAT(20, "应急处理中")/* 升级件 */,
	FIRST_LEVEL_CHECK_STAT(100, "一级事件核查"), SECOND_LEVEL_CHECK_STAT(101, "二级事件核查"), FIRST_TURNBACK_STAT(102, "一级核查未通过"),
	SECOND_TURNBACK_STAT(103, "二级核查未通过"), FINISH_STAT(1000, "关闭（办结）"), CANCEL_STAT(1001, "关闭（取消）"),;

	private Object value;

	private String desp;

	private EventEnum(Object value, String desp) {
		this.value = value;
		this.desp = desp;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public static List<Object> switchs(String status) {
		if (EventEnum.UNHANDLE.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.UNHANDLE_STAT.getValue());
		} else if (EventEnum.ALLOCATING.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.FIRST_LEVEL_ALLOCATING_STAT.getValue(),
					EventEnum.SECOND_LEVEL_ALLOCATING_STAT.getValue(), EventEnum.SERVICE_REFUSE_STAT.getValue());
		} else if (EventEnum.HANDLING.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.HANDLING_STAT.getValue(), EventEnum.FIRST_TURNBACK_STAT.getValue(),
					EventEnum.SECOND_TURNBACK_STAT.getValue(), EventEnum.SECOND_LEVEL_CHECK_STAT.getValue());
		} else if (EventEnum.UNCHECKED.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.FIRST_LEVEL_CHECK_STAT.getValue());
		} else if (EventEnum.REFUSE.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.SECOND_REFUSE_STAT.getValue());
		} else if (EventEnum.UPGRADE.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.EMERGENCY_STAT.getValue());
		} else if (EventEnum.CANCEL.getValue().equals(status)) {

			return CollUtil.newArrayList(EventEnum.CANCEL_STAT.getValue());
		} else if (EventEnum.FINISH.getValue().equals(status)) {
			return CollUtil.newArrayList(EventEnum.FINISH_STAT.getValue());
		} else {
			return CollUtil.newArrayList();
		}
	}
}
