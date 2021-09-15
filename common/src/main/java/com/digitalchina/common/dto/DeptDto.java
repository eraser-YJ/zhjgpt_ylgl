package com.digitalchina.common.dto;

import com.digitalchina.common.base.entity.tree.TreeEntity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 * @author warrior
 *
 * @since 2020年3月24日
 */

@ApiModel(value = "系统单位")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DeptDto extends TreeEntity<DeptDto, Integer> {
	/**
	* 
	*/
	private static final long serialVersionUID = -2772836086950228090L;

}
