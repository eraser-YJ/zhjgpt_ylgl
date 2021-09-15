package com.digitalchina.admin.mid.dto.layer;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * < 图层配置>
 * 
 * @author lihui
 * @since 2020年3月10日
 */
@Data
@ApiModel("图层配置")
@NoArgsConstructor
public class LayerConfig {

	public LayerConfig(String code) {
		super();
		this.code = code;
	}

	@ApiModelProperty("图层编码")
	private String code;

	@ApiModelProperty("条件")
	private List<LayerCondition> conditions;

	@ApiModelProperty("属性")
	private List<LayerProp> props;
}
