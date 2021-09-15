package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * <p>
 *  树节点权重
 * </p>
 *
 * @author liuping
 * @since 2019-10-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="树节点权重", description="")
public class NodeWeightDto implements Serializable {

    private static final long serialVersionUID = 680391922626499357L;

    @ApiModelProperty(value = "子节点id")
    @NotNull(message = "子节点id不能为空")
    private Integer id;

    @ApiModelProperty(value = "指标名称")
    @NotNull(message = "指标名称不能为空")
    private String nname;

    @ApiModelProperty(value = "权重")
    @NotNull(message = "权重不能为空")
    @Pattern(regexp = "((^0\\.\\d*[1-9]\\d*$)|1|(1\\.\\d*[0]))",message = "权重x必须是0<x<=1的数字")
    private String weight;
}
