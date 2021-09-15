package com.digitalchina.common.dto;

import com.digitalchina.common.utils.DoubleSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * <拥堵指数>
 * 
 * @author lihui
 * @since 2019年10月25日
 */
@Data
public class AmapYdzsDto {

	private String cityName; // 城市名

	private String dir; // 路段描述

	@JsonSerialize(using = DoubleSerialize.class)
	private Double idx; // 拥堵指数

	private Double speed; // 拥堵指数

	private String dt; // 日期时间戳

}
