package com.digitalchina.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <交通拥堵趋势>
 * 
 * @author lihui
 * @since 2019年11月7日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmapTrendDto {

	private String date;
	
	private Double val;
}
