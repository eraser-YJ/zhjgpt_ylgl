package com.digitalchina.modules.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;

import com.digitalchina.modules.entity.AppSysUser;
import com.digitalchina.modules.entity.AppZjUser;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ApiModel(value = "当前登录用户信息", description = "")
@NoArgsConstructor
public class UserDetail implements Serializable {

	private static final long serialVersionUID = -2465090938483636062L;

	@ApiModelProperty(value = "用户ID")
	private Integer id;

	@ApiModelProperty(value = "登陆名")
	private String login;

	@ApiModelProperty(value = "用户名")
	private String name;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "电话号码")
	private String tel;

	@ApiModelProperty(value = "用户角色")
	private String roles;

	@ApiModelProperty(value = "用户能访问的系统")
	private String apps;
	
	@ApiModelProperty(value = "是否从统一门户登录")
	private boolean loginFromPortal = false;

	@ApiModelProperty(value = "用户来源")
	private UserSource userSource;

	public UserDetail(SysUser user, List<SysRole> roles,List<String> apps,boolean loginFromPortal) {
		if (user != null) {
			BeanUtil.copyProperties(user, this);
		}
		if (CollectionUtil.isNotEmpty(roles)) {
			this.setRoles(CollectionUtil.join(roles.stream().map(SysRole::getCode).collect(Collectors.toList()), ","));
		}
		if (CollectionUtil.isNotEmpty(apps)) {
			this.setApps(CollectionUtil.join(apps, ","));
		}
		this.loginFromPortal = loginFromPortal;
	}

	public UserDetail(AppSysUser user, List<SysRole> roles, List<String> apps, boolean loginFromPortal) {
		if (user != null) {
			BeanUtil.copyProperties(user, this);
		}
		if (CollectionUtil.isNotEmpty(roles)) {
			this.setRoles(CollectionUtil.join(roles.stream().map(SysRole::getCode).collect(Collectors.toList()), ","));
		}
		if (CollectionUtil.isNotEmpty(apps)) {
			this.setApps(CollectionUtil.join(apps, ","));
		}
		this.loginFromPortal = loginFromPortal;
	}

	public UserDetail(AppZjUser user, List<SysRole> roles, List<String> apps, boolean loginFromPortal) {
		if (user != null) {
			BeanUtil.copyProperties(user, this);
		}
		if (CollectionUtil.isNotEmpty(roles)) {
			this.setRoles(CollectionUtil.join(roles.stream().map(SysRole::getCode).collect(Collectors.toList()), ","));
		}
		if (CollectionUtil.isNotEmpty(apps)) {
			this.setApps(CollectionUtil.join(apps, ","));
		}
		this.loginFromPortal = loginFromPortal;
	}

}
