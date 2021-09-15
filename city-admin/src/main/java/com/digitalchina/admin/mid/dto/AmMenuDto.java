package com.digitalchina.admin.mid.dto;


import cn.hutool.core.bean.BeanUtil;
import com.digitalchina.admin.mid.entity.AmSysMenu;
import com.digitalchina.common.base.entity.tree.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "菜单树", description = "")
@EqualsAndHashCode(callSuper = true)
public class AmMenuDto extends TreeEntity<AmMenuDto, Integer> {

    private static final long serialVersionUID = 2030915417443606899L;

    @ApiModelProperty(value = "菜单路径")
    private String url;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单序号")
    private Integer sort;

    @ApiModelProperty(value = "菜单状态 0：启用  1：禁用")
    private Integer status;

    @ApiModelProperty(value = "菜单备注")
    private String remark;

    @ApiModelProperty(value = "是否打勾")
    private Integer state;

    public AmMenuDto(AmSysMenu menu) {
        if (menu == null) {
            return;
        }
        BeanUtil.copyProperties(menu, this);
    }

}
