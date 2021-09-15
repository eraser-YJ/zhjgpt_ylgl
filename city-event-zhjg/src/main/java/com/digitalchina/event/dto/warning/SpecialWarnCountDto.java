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
 * 专题警告统计Dto
 * </p>
 *
 * @author zhaoyan liang
 * @since 2019-08-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("专题警告统计")
public class SpecialWarnCountDto {

    @ApiModelProperty(value = "专题名称")
    private String specialName;

    @ApiModelProperty(value = "预警数")
    private Long warningNumber;

    @ApiModelProperty(value = "报警数")
    private Long alarmNumber;

    @ApiModelProperty(value = "异常数")
    private Long exceptionNumber;



}
