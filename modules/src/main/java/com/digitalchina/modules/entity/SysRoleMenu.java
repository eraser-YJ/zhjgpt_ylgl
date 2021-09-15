package com.digitalchina.modules.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Data
@TableName("public.sys_role_menu")
@ApiModel(value = "SysRoleMenu对象", description = "")
@NoArgsConstructor
public class SysRoleMenu implements Serializable {

	private static final long serialVersionUID = 1L;

	public SysRoleMenu(Integer rid, Integer mid) {
		this.rid = rid;
		this.mid = mid;
	}

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "角色主键")
	private Integer rid;

	@ApiModelProperty(value = "菜单主键")
	private Integer mid;

	public static final String ID = "id";

	public static final String RID = "rid";

	public static final String MID = "mid";
}
