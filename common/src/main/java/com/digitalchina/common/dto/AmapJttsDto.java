package com.digitalchina.common.dto;

import lombok.Data;

/**
 * <高德地图交通态势>
 * 
 * @author lihui
 * @since 2019年10月24日
 */
@Data
public class AmapJttsDto {

	private String delayTime; // 延迟时间

	private String dir; // 行驶 (从小南街到金钱路-由西向东;

	private String index;// 拥堵指数( 1.9;

	private String length;// 长度 (3479.0;

	private String name;// 道路名(北环城路;

	private String number;// 排序(10;

	private String speed;// 速度(24.3;

	private String travelTime;// 穿过时间(8.6;
}
