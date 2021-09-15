package com.digitalchina.admin.config.security;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.digitalchina.admin.mid.entity.AmSysRole;
import com.digitalchina.admin.mid.entity.AmSysUser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "当前登录用户信息", description = "")
@NoArgsConstructor
public class AmUserDetail implements Serializable {

	private static final long serialVersionUID = -2465090938483636062L;

	@ApiModelProperty(value = "用户ID")
	private Integer id;

	@ApiModelProperty(value = "登陆名")
	private String login;

	@ApiModelProperty(value = "用户名")
	private String name;

	@ApiModelProperty(value = "用户角色code")
	private String roles;
	
	@ApiModelProperty(value = "‎电话")
	private String mobile;

	@ApiModelProperty(value = "备注")
	private String remark;

	public AmUserDetail(AmSysUser user, List<AmSysRole> roles) {
		if (user != null) {
			BeanUtil.copyProperties(user, this);
		}
		if (CollectionUtil.isNotEmpty(roles)) {
			this.setRoles(CollectionUtil.join(roles.stream().map(AmSysRole::getCode).collect(Collectors.toList()), ","));
		}
	}

}
