package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *  柱状图
 * </p>
 *
 * @author liuping
 * @since 2019-09-09
 */
@Data
@ApiModel(value = "HistogramDto对象", description = "")
public class HistogramDto  implements Serializable {

    private static final long serialVersionUID = 4602098297118247710L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "数值")
    private Float value;
}
