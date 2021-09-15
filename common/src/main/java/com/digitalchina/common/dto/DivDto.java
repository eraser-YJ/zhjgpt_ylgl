package com.digitalchina.common.dto;

import com.digitalchina.common.base.entity.tree.TreeEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 行政区划DTO
 * 
 * @author warrior
 *
 * @since 2020年3月23日
 */
@ApiModel(value = "行政区划")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DivDto extends TreeEntity<DivDto, Integer> {

	private static final long serialVersionUID = -7318995611135409531L;

	@ApiModelProperty(value = "节点值")
	private Integer value;

	@ApiModelProperty(value = "节点名称")
	private String label;

}
