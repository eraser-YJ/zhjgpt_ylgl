package com.digitalchina.event.dto.warning;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>
 * 处置列表Dto
 * </p>
 *
 * @author zhaoyan liang
 * @since 2019-08-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("处置列表")
public class DispositionListDto {

    @ApiModelProperty(value = "编号")
    private Integer wnid;

    @ApiModelProperty(value = "名称")
    private String wnnm;

    @ApiModelProperty(value = "内容")
    private String wncnt;

    /**
     * @see WnctyEnum
     */
    @ApiModelProperty(value = "类型(0预警、1报警、2异常)")
    private Integer wncty;

    @ApiModelProperty(value = "专题")
    private String special;

    @ApiModelProperty(value = "主题")
    private String topic;

    @ApiModelProperty(value = "预警时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crdt;

    @ApiModelProperty(value = "持续时间")
    private String continueTime;

    /**
     * @see WarningStateEnum
     */
    @ApiModelProperty(value = "处置状态(0未查看、1已查看、2关闭、3取消、11内部处理中，12内部已处理、21外部处理中，22外部已处理)")
    private Integer wnstat;

    @ApiModelProperty(value = "结束处理时间")
    private Date epdt;



}
