package com.digitalchina.common.dto;

import java.util.List;

import com.digitalchina.common.utils.DoubleSerialize;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <未来交通预测>
 * 
 * @author lihui
 * @since 2019年11月11日
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // jackson默认不忽略字段,类中找不到的字段会报错
public class AmapYlycDto {

	@ApiModelProperty("data")
	private List<AmapYdzsDto> idx;
	
	@JsonSerialize(using = DoubleSerialize.class)
	@ApiModelProperty("平均指数")
	private Double avg;
}
