package com.digitalchina.event.dto.eventdeposit;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("事件处置")
public class BusieventDto {

    @ApiModelProperty("序号")
    private Integer seq;

    @ApiModelProperty("事件id")
    Integer beid;

    @ApiModelProperty("流程实例编号")
    Integer procInstId;

    @ApiModelProperty("事件状态")
    Integer bestat;

    @ApiModelProperty("事件名称")
    String benm;

    @ApiModelProperty("开始时间")
    String hapndt;

    @ApiModelProperty("事件类型")
//    String etbh;
    String etnm;

    @ApiModelProperty("事件来源")
//    String efbh;
    String efnm;

    @ApiModelProperty("事件描述")
    String becnt;

    @ApiModelProperty("协查描述")
    String cpdesc;

    @ApiModelProperty("协查反馈")
    String cpfbinfo;


    @ApiModelProperty("所在区县名")
//    Integer adid;
    String adName;

    @ApiModelProperty("所在乡镇名")
//    Integer townAdid;
    String townName;

    @ApiModelProperty("转派部门/受理部门/办理部门/申请部门/协查部门")
    String bepcdpt0Name;

    @ApiModelProperty("受理时间/完成时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    Date acceptanceTime;

    @ApiModelProperty("完成反馈/拒绝理由/取消原因/升级原因/申请原因/复查原因/协查反馈/驳回原因")
    String reason;

}
