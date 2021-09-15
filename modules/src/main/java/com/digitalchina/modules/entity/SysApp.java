package com.digitalchina.modules.entity;

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
 * @since 2019-08-19
 */
@Data
@TableName("sys_app")
@ApiModel(value = "SysApp对象", description = "")
public class SysApp implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "应用code")
	private String code;

	@ApiModelProperty(value = "系统名称")
	private String name;

	@ApiModelProperty(value = "系统连接")
	private String url;

	@ApiModelProperty(value = "备注")
	private String remark;

	public static final String ID = "id";

	public static final String CODE = "code";

	public static final String URL = "url";

	public static final String NAME = "name";

	public static final String REMARK = "remark";

}
