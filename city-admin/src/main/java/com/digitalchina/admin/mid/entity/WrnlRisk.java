package com.digitalchina.admin.mid.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.mid.dto.warn2.WarnRiskDetailDto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("warn.warn_wrnl_risk")
@ApiModel(value = "WrnlRisk对象", description = "")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WrnlRisk implements Serializable {

	private static final long serialVersionUID = 1L;
	public static WrnlRisk conver(WarnRiskDetailDto s) {
		WrnlRisk t = new WrnlRisk();
		t.setId(s.getId());
		if (null == s.getId()) {
			t.setCrdt(new Date());
		}
		t.setModt(new Date());
		t.setFrequency(s.getFrequency());
		t.setModelid(s.getModelid());
		t.setSysremind(Boolean.TRUE);// 系统默认提醒
		return t;
	}

	@TableId
	private Integer id;

	@ApiModelProperty(value = "预警模型ID")
	private Integer modelid;

	@ApiModelProperty(value = "周期（）")
	private String cycle;

	@ApiModelProperty(value = "频率")
	private String frequency;

	@ApiModelProperty("天（间隔天数）")
	private Integer intervaldays;

	@ApiModelProperty("指定每月几号（1~31）")
	private Integer[] fixdays;

	@ApiModelProperty("周（星期一~星期日：1~7）")
	private Integer[] week;

	@ApiModelProperty("月（间隔月数）")
	private Integer month;

	@ApiModelProperty("时间（实时：空时间，定时/时间段：无逗号/逗号隔开）")
	private String time;

	@ApiModelProperty(value = "系统提醒（是否）")
	private Boolean sysremind;

	@ApiModelProperty(value = "用户提醒（是否）")
	private Boolean userremind;

	@ApiModelProperty(value = "启用停用")
	private Integer status;

	@ApiModelProperty(value = "上次提醒时间")
	private Date lsttime;

	@ApiModelProperty(value = "下次提醒时间")
	private Date nexttime;

	@ApiModelProperty(value = "提醒用户")
	private Integer[] deptid;

	private Date crdt;

	private Date modt;

	@ApiModelProperty("指标")
	@TableField(exist = false)
	private String menames;

	@ApiModelProperty("组件/监测点名称")
	@TableField(exist = false)
	private String itemname;

	@ApiModelProperty("组件/监测点类型")
	@TableField(exist = false)
	private String itemtype;

	@ApiModelProperty("模型名称")
	@TableField(exist = false)
	private String modelname;

	@ApiModelProperty("专题")
	@TableField(exist = false)
	private String special;

	@ApiModelProperty("主题")
	@TableField(exist = false)
	private String topic;

	public static final String ID = "id";

	public static final String ITEMID = "itemid";

	public static final String TYPE = "type";

	public static final String MODELID = "modelid";

	public static final String CYCLE = "cycle";

	public static final String FREQUENCY = "frequency";

	public static final String SYSREMIND = "sysremind";

	public static final String USERREMIND = "userremind";

	public static final String STATUS = "status";

	public static final String LSTTIME = "lsttime";

	public static final String NEXTTIME = "nexttime";

	public static final String USERID = "userid";
}
