package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 折线图
 * </p>
 *
 * @author liuping
 * @since 2019-08-30
 */
@Data
@ApiModel(value = "LineDto对象", description = "")
public class LineDto {

    @ApiModelProperty(value = "x轴坐标")
    List<String> xData;

    @ApiModelProperty(value = "y轴数据")
    List<SerieDto> yData;
}
