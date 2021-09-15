package com.digitalchina.modules.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Data
@TableName("public.sys_role_user")
@ApiModel(value = "SysRoleUser对象", description = "")
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleUser implements Serializable {

	private static final long serialVersionUID = 1L;

	public SysRoleUser(Integer uid, Integer rid) {
		this.uid = uid;
		this.rid = rid;
	}

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "用户ID")
	private Integer uid;

	@ApiModelProperty(value = "角色ID")
	private Integer rid;

	public static final String ID = "id";

	public static final String UID = "uid";

	public static final String RID = "rid";
}
