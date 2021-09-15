package com.digitalchina.event.dto.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <类型级别Dto>
 * 
 * @author lihui
 * @since 2019年8月1日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("类型隶属")
public class TypeBelongDto {

	@ApiModelProperty("ID")
	private Integer id;

	@ApiModelProperty("名称")
	private String name;
	
	@ApiModelProperty("级别")
	private Integer level;
}
