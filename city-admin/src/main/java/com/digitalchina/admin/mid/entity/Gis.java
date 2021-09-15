package com.digitalchina.admin.mid.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <坐标对象>
 * 
 * @author lihui
 * @since 2019年8月8日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("坐标")
public class Gis {

	public Gis(String longitude, String latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}

	@ApiModelProperty("类型,只是参考(point:点)")
	private String type;

	@ApiModelProperty("经度")
	private String longitude;

	@ApiModelProperty("纬度")
	private String latitude;

	@ApiModelProperty("坐标")
	private String[] coordinates;
}
