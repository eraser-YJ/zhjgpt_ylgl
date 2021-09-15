package com.digitalchina.common.dto;

import java.util.List;

import lombok.Data;

/**
 * <高德交通态势>
 * 
 * @author lihui
 * @since 2019年10月24日
 */
@Data
public class AmapDto {

	/**
	 * 最近更新时间
	 */
	private String updateTime;

	/**
	 * 交通态势数据
	 */
	private List<AmapJttsDto> tableData;
}
