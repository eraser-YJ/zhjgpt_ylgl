package com.digitalchina.event.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *      自定义考核dto
 * </p>
 *
 * @author liuping
 * @since 2019-09-11
 */
@Data
@ApiModel(value = "自定义考核dto", description = "")
public class CustomAssessmentDto implements Serializable {

    @ApiModelProperty(value = "名称： 区域/部门")
    private String name;

    @ApiModelProperty(value = "部门类型")
    private Integer bdtype;

    @ApiModelProperty(value = "事件总数")
    private Integer count;

    @ApiModelProperty(value = "处理率")
    private String handlerper;

    @ApiModelProperty(value = "处理率分值")
    private Integer handlergrade;

    @ApiModelProperty(value = "按期处理率")
    private String inhandlerper;

    @ApiModelProperty(value = "按期处理率分值")
    private Integer inhandlergrade;

    @ApiModelProperty(value = "完成率")
    private String closeper;

    @ApiModelProperty(value = "完成率分值")
    private Integer closegrade;

    @ApiModelProperty(value = "按期完成率")
    private String incloseper;

    @ApiModelProperty(value = "按期完成率分值")
    private Integer inclosegrade;

    @ApiModelProperty(value = "综合分值")
    private Integer synthesis;
}
