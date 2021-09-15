package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@TableName("public.sys_user")
@ApiModel(value = "Sysuser对象", description = "")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID")
	private Integer id;

	@JsonIgnore
	@ApiModelProperty(value = "统一认证平台的用户ID")
	private String ssoid;

	@ApiModelProperty(value = "登陆名")
	private String login;

	@JsonIgnore
	@ApiModelProperty(value = "登陆密码")
	private String password;

	@ApiModelProperty(value = "用户名")
	private String name;

	@ApiModelProperty(value = "用户状态 0：正常 1：冻结")
	private Integer status;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "电话号码")
	private String tel;

	@ApiModelProperty(value = "描述，备注")
	private String mark;

	@ApiModelProperty(value = "部门id")
	private Integer dpid;

	@ApiModelProperty(value = "上级部门ID清单")
	private Integer[] bdpntids;

	/***
	 * 
	 * 拓展字段
	 * 
	 */

	@TableField(exist = false)
	@ApiModelProperty(value = "部门名称")
	private String bdnm;

	@ApiModelProperty(value = "角色对象")
	@TableField(exist = false)
	private List<SysRole> roleList;

	public static final String ID = "id";

	public static final String SSOID = "ssoid";

	public static final String LOGIN = "login";

	public static final String PASSWORD = "password";

	public static final String NAME = "name";

	public static final String STATUS = "status";

	public static final String AVATAR = "avatar";

	public static final String TEL = "tel";

	public static final String DESC = "desc";

}
