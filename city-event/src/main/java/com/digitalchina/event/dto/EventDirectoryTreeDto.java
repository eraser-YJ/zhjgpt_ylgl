package com.digitalchina.event.dto;


import com.digitalchina.common.base.entity.tree.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@ApiModel(value = "城市事件目录结构树", description = "")
@EqualsAndHashCode(callSuper = true)
public class EventDirectoryTreeDto extends TreeEntity<EventDirectoryTreeDto, String> {
    private static final long serialVersionUID = 2030915417443606899L;

    @ApiModelProperty(value = "类型（0跟节点，1区域节点，2部门节点，3事项类型节点）")
    private Integer type;

    private Integer deid;

}
