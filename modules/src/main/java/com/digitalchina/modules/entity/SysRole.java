package com.digitalchina.modules.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
@Data
@TableName("public.sys_role")
@ApiModel(value = "Sysrole对象", description = "")
public class SysRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "角色ID")
	private Integer id;

	@ApiModelProperty(value = "角色类型 0：正常角色  1：不允许删除和修改")
	private Integer type;

	@ApiModelProperty(value = "0：非默认角色 1：默认角色")
	private Integer dftype;

	@ApiModelProperty(value = "角色名称")
	private String code;

	@ApiModelProperty(value = "角色名称")
	private String name;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "是否打勾")
	@TableField(exist = false)
	private Integer state;

	public static final String ID = "id";

	public static final String TYPE = "type";

	public static final String DFTYPE = "dftype";

	public static final String CODE = "code";

	public static final String NAME = "name";

	public static final String REMARK = "remark";

}
