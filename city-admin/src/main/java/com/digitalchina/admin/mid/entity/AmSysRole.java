package com.digitalchina.admin.mid.entity;

import java.io.Serializable;

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
 * @since 2019-08-27
 */
@Data
@TableName("admin.am_sys_role")
@ApiModel(value = "AmSysRole对象", description = "")
public class AmSysRole implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "角色类型 0：不允许删除和修改  1：正常角色")
	private Integer type;
	
	@ApiModelProperty(value = "角色名称")
	private String code;

	@ApiModelProperty(value = "角色名称")
	private String name;

	@ApiModelProperty(value = "备注")
	private String remark;

	public static final String ID = "id";

	public static final String TYPE = "type";

	public static final String CODE = "code";

	public static final String NAME = "name";

	public static final String REMARK = "remark";

}
