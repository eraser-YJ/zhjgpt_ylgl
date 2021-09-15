package com.digitalchina.event.dto.warning;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <设备查询请求参数>
 *
 * @author lichunlong
 * @since 2019年8月12日
 */
@Data
@ApiModel("图层查询入参")
public class QueryLayerRequest {

    @ApiModelProperty(value = "专题名称",required = true)
    private String special;

    @ApiModelProperty("主题名称")
    private String theme;

    @ApiModelProperty("图层名称")
    private String lynm;

    @ApiModelProperty("部门id")
    private Integer cmnid;

}
