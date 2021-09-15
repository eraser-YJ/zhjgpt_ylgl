package com.digitalchina.admin.mid.dto.warn2;

import java.util.List;

import com.digitalchina.modules.constant.enums.SourceTypeEnum;

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

	@ApiModelProperty("预警总数")
	private Integer warnnum = 0;

	@ApiModelProperty("展示方式 map table")
	private String mode;

	/**
	 * @see SourceTypeEnum
	 */
	@ApiModelProperty("数据来源类型(0:物联网 1:互联网 2:业务系统)")
	private Integer srctype;

	@ApiModelProperty("子类型")
	private List<TypeDto> child;

	public TypeDto(String name, String mode, Integer srctype, List<TypeDto> child) {
		super();
		this.name = name;
		this.mode = mode;
		this.srctype = srctype;
		this.child = child;
	}

}
