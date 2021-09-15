package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 登录日志表
 * </p>
 *
 * @author Warrior
 * @since 2019-08-29
 */
@TableName("public.sys_log")
@ApiModel(value = "SysLog对象", description = "登录日志表")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "用户id")
	private Integer userid;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "登录名")
	private String login;

	@ApiModelProperty(value = "角色名称，多个角色逗号分隔")
	private String roles;

	@ApiModelProperty(value = "系统码")
	private String syscode;

	@ApiModelProperty(value = "系统名称")
	private String sysname;

	@ApiModelProperty(value = "操作日志")
	private String actlog;

	@ApiModelProperty(value = "操作结果(0 失败 1成功)")
	private Integer result;

	@ApiModelProperty(value = "操作时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date actime;

	@ApiModelProperty(value = "IP地址")
	private String ipaddr;

	@ApiModelProperty(value = "返回信息")
	private String message;

	/**
	 * 状态 @see StateEnum
	 */
	private Integer state;

	public static final String ID = "id";

	public static final String USERID = "userid";

	public static final String USERNAME = "username";

	public static final String LOGIN = "login";

	public static final String ROLES = "roles";

	public static final String SYSCODE = "syscode";

	public static final String SYSNAME = "sysname";

	public static final String ACTLOG = "actlog";

	public static final String RESULT = "result";

	public static final String ACTIME = "actime";

	public static final String IPADDR = "ipaddr";

	public static final String MESSAGE = "message";

}
