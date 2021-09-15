package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色菜单dto
 *
 * @author warrior
 * @since 2019年2月14日
 */
@ApiModel(value = "角色授权参数")
@Data
public class RoleMenuDto implements Serializable {

    private static final long serialVersionUID = -2213440635082512790L;

    @ApiModelProperty("角色ID")
    private Integer roleId;

    @ApiModelProperty("菜单ID列表")
    private List<Integer> menuIdList;
}
