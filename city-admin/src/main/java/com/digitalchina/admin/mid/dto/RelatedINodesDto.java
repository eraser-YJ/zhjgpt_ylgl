package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  管理关联页面Dto
 * </p>
 *
 * @author liuping
 * @since 2019-10-16
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RelatedINodesDto对象", description="管理关联页面Dto")
public class RelatedINodesDto implements Serializable {

    private static final long serialVersionUID = 632752146987155111L;

    @ApiModelProperty(value = "父节点pid")
    @NotNull(message = "父节点pid不能为空")
    private Integer pid;

    @ApiModelProperty(value = "父节点指标码")
    private String ncode;

    @ApiModelProperty(value = "父节点指标名称")
    private String nname;

    @ApiModelProperty(value = "未选列表(左侧)")
    private List<RelatedItemDto> uncheckList;

    @Valid
    @ApiModelProperty(value = "已选列表(右侧)")
    private List<RelatedItemDto> checkList;
}
