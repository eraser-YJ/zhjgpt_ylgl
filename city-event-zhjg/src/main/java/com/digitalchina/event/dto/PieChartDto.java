package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *  饼状图
 * </p>
 *
 * @author liuping
 * @since 2019-09-09
 */
@Data
@ApiModel(value = "PieChartDto对象", description = "")
public class PieChartDto implements Serializable {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数值")
    private Integer value;

    @ApiModelProperty(value = "百分比")
    private BigDecimal per;

}
