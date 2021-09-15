package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 树某个子节点权重信息
 * </p>
 *
 * @author liuping
 * @since 2019-10-15
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="树某个子节点权重信息", description="")
public class SongNodesDto implements Serializable {

    private static final long serialVersionUID = -5826219400333170450L;

    @ApiModelProperty(value = "父节点pid")
    @NotNull(message = "父节点pid不能为空")
    private Integer pid;

    @Valid
    @ApiModelProperty(value = "子节点列表")
    @NotEmpty(message = "子节点列表不能为空")
    private List<NodeWeightDto> childList;
}
