package com.digitalchina.modules.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据字典dto类
 * 
 * @author warrior
 *
 * @since 2020年3月19日
 */
@Data
@ApiModel(value = "数据字典对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDto implements Serializable {

	private static final long serialVersionUID = -5251031029392119937L;

	@ApiModelProperty(value = "字典项名称")
	private String itemName;

	@ApiModelProperty(value = "字典项值")
	private String itemValue;

}
