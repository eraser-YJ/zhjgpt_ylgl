package com.digitalchina.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * <高德空气质量>
 * 
 * @author lihui
 * @since 2019年11月4日
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true) // jackson默认不忽略字段,类中找不到的字段会报错
public class AmapKqzlDto {

	private String city;// 城市

	private String start_time;// 开始时间

	private String valid_days; // 未来天数

	@JsonProperty("AQI")
	private String aqi;// aqi

	@JsonProperty("AQI_grade")
	private Integer aqi_grade;// api级别
}
