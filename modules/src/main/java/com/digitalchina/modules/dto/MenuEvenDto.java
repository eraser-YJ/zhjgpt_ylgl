package com.digitalchina.modules.dto;


import cn.hutool.core.bean.BeanUtil;
import com.digitalchina.common.base.entity.tree.TreeEntity;
import com.digitalchina.modules.entity.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value = "奕迅事件菜单树", description = "")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MenuEvenDto extends TreeEntity<MenuEvenDto, Integer> {

    private static final long serialVersionUID = 2030915417443606899L;

    @ApiModelProperty(value = "编号")
    private Integer no;

    @ApiModelProperty(value = "时间")
    private String time;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "地址")
    private String handleUrl;


    public MenuEvenDto(SysMenu menu) {
        if (menu == null) {
            return;
        }
        BeanUtil.copyProperties(menu, this);
    }

}
