package com.digitalchina.event.dto.warning;

import io.swagger.annotations.ApiImplicitParam;
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
@ApiModel("预警查询入参")
public class QueryWarnRequest {

    @ApiModelProperty(value = "专题名称",required = true)
    private String special;
    @ApiModelProperty("主题名称")
    private String theme;
    @ApiModelProperty("异常类型")
    private Integer wncty;
    @ApiModelProperty("办结状态")
    private Integer wnstat;
    @ApiModelProperty("行政区划")
    private Integer adid;
    @ApiModelProperty("所属部门")
    private Integer cmnid;
    @ApiModelProperty("预警名称")
    private String wnnm;

}
