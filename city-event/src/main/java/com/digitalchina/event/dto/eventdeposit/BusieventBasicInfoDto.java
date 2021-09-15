package com.digitalchina.event.dto.eventdeposit;

import com.digitalchina.event.mid.entity.Gis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("事件基本信息")
public class BusieventBasicInfoDto {
	@ApiModelProperty("事件id")
	private Integer beid;

	@ApiModelProperty("事件编号")
	private String bebh;

	@ApiModelProperty(value = "事件名称")
	private String benm;

	@ApiModelProperty(value = "事件类型")
	private String etbhName;

	@ApiModelProperty(value = "发生时间")
	private String hapndt;

	@ApiModelProperty(value = "发生地点")
	private String addr;

	@ApiModelProperty(value = "事件来源")
	private String efbhName;

	@ApiModelProperty("处置状态")
	private String bestat;

	@ApiModelProperty(value = "当前阶段责任部门")
	private String besrcdptName;

	@ApiModelProperty(value = "当前阶段开始时间")
	private String processingBegin;

	@ApiModelProperty(value = "当前阶段已处理时长")
	private String processingTime;

	@ApiModelProperty(value = "累计处理时长")
	private String ljProcessingTime;

	@ApiModelProperty(value = "经度")
	private String lon;

	@ApiModelProperty(value = "纬度")
	private String lat;

	@ApiModelProperty(value = "所在区县")
	private String adidName;

	@ApiModelProperty(value = "所在乡镇")
	private String townAdidName;

	@ApiModelProperty(value = "经派机构")
	private String bedptsName;

	@ApiModelProperty(value = "受理部门")
	private String bepcdptName;

	@ApiModelProperty(value = "事件创建时间")
	private String crdt;

	@ApiModelProperty(value = "受理时间")
	private String slsj;

	@ApiModelProperty(value = "关闭时间")
	private String clsdt;

	@ApiModelProperty(value = "处置时长")
	private String pcdura;

	@ApiModelProperty(value = "是否超期")
	private String isExceeded;

	@ApiModelProperty(value = "事件描述")
	private String becnt;

	@ApiModelProperty(value = "经纬度(前端不用)")
	private Gis bexy;

	@ApiModelProperty(value = "远程附件ID_文件类型，多个以逗号隔开")
	private String fileids;
	
	@ApiModelProperty(value = "性质分类")
	private Integer type;

}
