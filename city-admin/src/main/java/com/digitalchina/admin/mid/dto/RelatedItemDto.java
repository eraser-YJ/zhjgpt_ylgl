package com.digitalchina.admin.mid.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *  体征关联数据项
 * </p>
 *
 * @author liuping
 * @since 2019-10-16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="RelatedItemDto对象", description="体征关联数据项")
public class RelatedItemDto implements Serializable {

    private static final long serialVersionUID = -3788321905655893626L;

    @ApiModelProperty(value = "指标码")
    @NotNull(message = "指标码不能为空")
    private String ncode;

    @ApiModelProperty(value = "指标名称")
    private String nname;
}
