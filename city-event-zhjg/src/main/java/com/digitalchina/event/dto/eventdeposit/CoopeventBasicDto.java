package com.digitalchina.event.dto.eventdeposit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("协查事件处置")
public class CoopeventBasicDto {

    @ApiModelProperty("协查事件id")
    Integer ceid;

    @ApiModelProperty("事件编号")
    String cebh;

    @ApiModelProperty("事件名称")
    String cenm;

    @ApiModelProperty("事件类型")
    String etnm;

    @ApiModelProperty("开始时间")
    String hapndt;

    @ApiModelProperty("发生地点")
    String addr;

    @ApiModelProperty("所在区县名")
    String adName;

    @ApiModelProperty("事件状态")
    String cestat;

    @ApiModelProperty("事件状态名")
    String cestatName;

    @ApiModelProperty("所在乡镇名")
    String townName;

    @ApiModelProperty("当前阶段责任部门")
    String cesrcdptName;

    @ApiModelProperty(value = "当前阶段已处理时长")
    String processingTime;

    @ApiModelProperty(value = "累计处理时长")
    String ljProcessingTime;

    @ApiModelProperty("事件来源")
    String efnm;

    @ApiModelProperty("事件描述")
    String cecnt;

    @ApiModelProperty("协查描述")
    String cpdesc;

    @ApiModelProperty("申请部门/协查部门")
    String cpbedidName;

    @ApiModelProperty("协查描述/协查反馈")
    String cpfbinfo;

    @ApiModelProperty(value = "远程附件ID_文件类型，多个以逗号隔开")
    private String fileids;

}
