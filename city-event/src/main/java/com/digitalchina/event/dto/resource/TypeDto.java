package com.digitalchina.event.dto.resource;

import java.util.List;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <类型Dto>
 * 
 * @author lihui
 * @since 2019年8月1日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("类型")
public class TypeDto {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("隶属于哪个维度下")
	private TypeBelongDto belong;

//	/**
//	 * @see SourceTypeEnum
//	 */
//	@ApiModelProperty("数据来源类型(0:物联网 1:互联网 2:业务系统)")
//	private Integer srctype;

	@ApiModelProperty("子类型")
	private List<TypeDto> child;
}
