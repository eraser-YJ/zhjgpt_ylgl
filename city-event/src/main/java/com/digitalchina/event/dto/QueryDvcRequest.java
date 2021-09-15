package com.digitalchina.event.dto;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <设备查询请求参数>
 * 
 * @author lihui
 * @since 2019年8月9日
 */
@Data
@ApiModel("查询设备入餐")
public class QueryDvcRequest {

	@ApiModelProperty("经度")
	private BigDecimal longitude;

	@ApiModelProperty("纬度")
	private BigDecimal latitude;

	@ApiModelProperty("半径(单位:米)")
	private BigDecimal radius;
}
