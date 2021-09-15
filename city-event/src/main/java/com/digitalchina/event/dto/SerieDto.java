package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *  折线图y轴数据
 * </p>
 *
 * @author liuping
 * @since 2019-08-30
 */
@Data
@ApiModel(value = "SerieDto对象", description = "")
public class SerieDto<T> {

    @ApiModelProperty(value = "折线名称")
    String name;

    @ApiModelProperty(value = "折线数组数据")
    List<T> data;

    @ApiModelProperty(value = "额外描述 非必输字段",required = false)
    String desc;
}
