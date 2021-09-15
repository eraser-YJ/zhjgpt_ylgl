package com.digitalchina.event.dto.eventdeposit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("协查事件处置")
public class CoopeventDto {

    @ApiModelProperty("序号")
    private Integer seq;

    @ApiModelProperty("协查事件id")
    Integer ceid;

    @ApiModelProperty("事件名称")
    String cenm;

    @ApiModelProperty("开始时间")
    String hapndt;

    @ApiModelProperty("事件类型")
//    String etbh;
    String etnm;

    @ApiModelProperty("事件来源")
//    String efbh;
    String efnm;

    @ApiModelProperty("事件描述")
    String cecnt;

    @ApiModelProperty("协查描述")
    String cpdesc;

    @ApiModelProperty("申请部门/协查部门")
    String cpbedidName;

    @ApiModelProperty("协查描述/协查反馈")
//    Integer townAdid;
    String cpfbinfo;

}
