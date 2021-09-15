package com.digitalchina.event.dto.warning;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <设备查询请求参数>
 *
 * @author lichunlong
 * @since 2019年8月12日
 */
@Data
@ApiModel("视频监控查询入参")
public class QueryVideoRequest {

    @ApiModelProperty(value = "专题名称", required = true)
    private String special;
    @ApiModelProperty("主题名称")
    private String theme;
    @ApiModelProperty("设备位置")
    private String idaddr;
    @ApiModelProperty("委办局id")
    private Integer cmnid;
    @ApiModelProperty("主干道")
    private String road;
    @ApiModelProperty("重点区域")
    private String iparea;

}
