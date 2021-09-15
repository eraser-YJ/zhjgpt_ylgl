package com.digitalchina.modules.dto;


import com.digitalchina.modules.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "菜单树", description = "")
public class MenuListDto {

    @ApiModelProperty(value = "角色ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单树")
    private List<SysMenu> menuList;

}
