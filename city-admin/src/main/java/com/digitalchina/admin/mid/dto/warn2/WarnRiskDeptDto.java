package com.digitalchina.admin.mid.dto.warn2;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <部门>
 * 
 * @author lihui
 * @since 2020年1月3日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarnRiskDeptDto {

	@ApiModelProperty("部门ID")
	private Integer id;

	@ApiModelProperty("名称")
	private String name;
}
