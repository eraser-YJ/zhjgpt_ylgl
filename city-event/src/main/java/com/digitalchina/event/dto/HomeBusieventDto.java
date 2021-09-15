package com.digitalchina.event.dto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.digitalchina.event.mid.entity.Gis;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lzy
 * @since 2019/9/9
 */
@Data
public class HomeBusieventDto {
    @ApiModelProperty(value = "事件ID")
    private Integer beid;

    @ApiModelProperty(value = "事件名称")
    private String benm;

    @ApiModelProperty(value = "事件类型")
    private String etbhName;

    @ApiModelProperty(value = "发生时间")
    private String hapndt;

    @ApiModelProperty(value = "创建时间")
    private String crdt;

    @ApiModelProperty(value = "发生地点")
    private String addr;

    @ApiModelProperty(value = "事件来源")
    private String efbhName;

    @ApiModelProperty(value = "当前状态")
    private String bestat;

    @ApiModelProperty(value = "当前状态名称")
    private String bestatName;

    @ApiModelProperty(value = "当前阶段责任部门")
    private String bepcdptName;

    @ApiModelProperty(value = "当前阶段已处理时长")
    private String pcdura;

    @ApiModelProperty(value = "累计处理时长")
    private String alldura;

    @ApiModelProperty(value = "经纬度")
    private Gis bexy;

    @ApiModelProperty(value = "远程附件ID，多个以逗号隔开")
    private String fileids;

}
