package com.digitalchina.event.dto;

import com.digitalchina.event.mid.entity.Attachment;
import com.digitalchina.event.mid.entity.Gis;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  推送至应急系统的事件Dto
 * </p>
 *
 * @author liuping
 * @since 2019-12-03
 */
@Data
public class EmergencyInfoDto implements Serializable {

    private static final long serialVersionUID = -5254592177761757377L;

    @ApiModelProperty(value = "事件系统ID")
    private Integer beid;

    @ApiModelProperty(value = "事件名称")
    private String benm;

    @ApiModelProperty(value = "事件编号")
    private String bebh;

    @ApiModelProperty(value = "事项系统类型代码")
    private Integer etbh;

    @ApiModelProperty(value = "事项系统类型名称")
    private String etnm;

    @ApiModelProperty(value = "事件发生时间")
    private Date hapndt;

    @ApiModelProperty(value = "事件上报时间")
    private Date rpdt;

    @ApiModelProperty(value = "事件系统区划ID")
    private Integer adid;

    @ApiModelProperty(value = "事件系统区划全称")
    private String adflnm;

    @ApiModelProperty(value = "事件发生地址")
    private String addr;

    @ApiModelProperty(value = "经纬度")
    private Gis bexy;

    @ApiModelProperty(value = "事件描述")
    private String becnt;

    List<Attachment> attachmentList;
}
