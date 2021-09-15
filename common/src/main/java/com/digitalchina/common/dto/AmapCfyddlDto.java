package com.digitalchina.common.dto;

import com.digitalchina.common.utils.DoubleSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <高德常发拥堵道路>
 * 
 * @author lihui
 * @since 2019年11月7日
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // jackson默认不忽略字段,类中找不到的字段会报错
public class AmapCfyddlDto {

	@ApiModelProperty("序号")
	private Integer order;//
	
	@ApiModelProperty("道路名")
	private String name;//

	@ApiModelProperty("路段描述")
	private String description;//

	@JsonSerialize(using=DoubleSerialize.class)
	@ApiModelProperty("拥堵指数")
	private Double peakIdx;//

	@JsonSerialize(using=DoubleSerialize.class)
	@ApiModelProperty("速度")
	private Double peakSpeed;//

	@ApiModelProperty("延时时间")
	private String congestHours;//
}
