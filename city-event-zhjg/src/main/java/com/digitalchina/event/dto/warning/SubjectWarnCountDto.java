package com.digitalchina.event.dto.warning;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 主题警告统计Dto
 * </p>
 *
 * @author zhaoyan liang
 * @since 2019-08-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("主题警告统计")
public class SubjectWarnCountDto {

    @ApiModelProperty(value = "主题预警总数")
    private Integer subjectNumber;

    @ApiModelProperty(value = "预警数")
    private Integer warningNumber;

    @ApiModelProperty(value = "报警数")
    private Integer alarmNumber;

    @ApiModelProperty(value = "异常数")
    private Integer exceptionNumber;



}
