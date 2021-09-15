package com.digitalchina.admin.mid.dto.warn2;

import java.util.List;

import com.digitalchina.admin.mid.dto.SignTreeDto.node;
import com.digitalchina.admin.mid.entity.Iotdvc;
import com.digitalchina.admin.mid.entity.Mesu;
import com.digitalchina.admin.mid.entity.warn.Nfelement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <预警指标明细>
 * 
 * @author lihui
 * @since 2019年12月27日
 */
@Data
@node
@AllArgsConstructor
@ApiModel("预警指标明细")
public class WarnMesuDetailDto {

	public WarnMesuDetailDto(Iotdvc s) {
		this.itemid = s.getIdid();
		this.name = s.getIdnm();
		this.type = "监测点";
		this.frequency = "实时";
		this.defshape = "GIS";
		this.othershape = "无";
	}

	public WarnMesuDetailDto(Nfelement s) {
		this.itemid = s.getId();
		this.name = s.getName();
		this.source = s.getSource();
		if (null != s.getCpvs() && s.getCpvs().length > 0) {
			this.special = s.getCpvs()[0];
			this.topic = s.getCpvs()[1];
		}
		this.type = s.getType();
		this.frequency = s.getFrequency();
		this.defshape = s.getDefshape();
		this.othershape = s.getOthershape();
	}

	@ApiModelProperty("id")
	Integer itemid;

	@ApiModelProperty("名称")
	String name;

	@ApiModelProperty("专题")
	String special;

	@ApiModelProperty("主题")
	String topic;

	@ApiModelProperty("类型")
	String type;

	@ApiModelProperty("更新频率")
	String frequency;

	@ApiModelProperty("默认形状")
	String defshape;

	@ApiModelProperty("其他形状")
	String othershape;

	@ApiModelProperty("数据源（委办局")
	String source;

	@ApiModelProperty("数据源（表名")
	String tb;

	@ApiModelProperty("说明")
	String explain;

	@ApiModelProperty("指标")
	List<Mesu> mesus;

	@ApiModelProperty("模型")
	String models;
}
