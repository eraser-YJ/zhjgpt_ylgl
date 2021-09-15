package com.digitalchina.admin.mid.dto.warn2;

import com.digitalchina.admin.mid.dto.SignTreeDto.node;
import com.digitalchina.admin.mid.entity.warn.Nfelement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <预警指标>
 * 
 * @author lihui
 * @since 2019年12月27日
 */
@Data
@node
@AllArgsConstructor
@ApiModel("预警指标")
public class WarnMesuDto {

	public WarnMesuDto(Nfelement d) {
		super();
		this.id = d.getId();
		this.special = d.getCpvs()[0];
		this.topic = d.getCpvs()[1];
		this.name = d.getName();
	}

	Integer id;
	@ApiModelProperty("专题")
	String special;

	@ApiModelProperty("主题")
	String topic;

	@ApiModelProperty("委办局名称")
	String cmnnm;

	@ApiModelProperty("名称")
	String name;

	@ApiModelProperty("指标")
	String menames;

}
