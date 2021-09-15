package com.digitalchina.admin.mid.dto.layer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <图层列表请求参数>
 * 
 * @author lihui
 * @since 2020年3月11日
 */
@Data
@ApiModel("列表请求参数")
public class LayerListRequest {

	@ApiModelProperty("配置")
	private LayerConfig config;
	@ApiModelProperty("一页展示的数量")
	private Integer size = 10;
	@ApiModelProperty("当前页数")
	private Integer current = 1;

}
