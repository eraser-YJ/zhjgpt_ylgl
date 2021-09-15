package com.digitalchina.admin.mid.dto;

import java.io.Serializable;
import java.util.Date;

import com.digitalchina.admin.mid.entity.Gis;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 事件查询列表的DTO
 * </p>
 *
 * @author liuping
 * @since 2019-12-16
 */
@Data
@ApiModel(value="事件查询列表的DTO")
public class EmEventDto implements Serializable {

    private static final long serialVersionUID = 5263148549310091279L;

    @ApiModelProperty(value = "应急事件编号")
    private Integer evid;

    @ApiModelProperty(value = "预应急事件编号")
    private Integer previd;

    @ApiModelProperty(value = "事件名称")
    private String ename;

    @ApiModelProperty(value = "事件编号")
    private String eno;

    @ApiModelProperty(value = "事件类型")
    private Integer etypefk;

    @ApiModelProperty(value = "事件等级")
    private Integer elevelfk;

    @ApiModelProperty(value = "事发地点")
    private String hpnaddr;

    @ApiModelProperty(value = "事发区域（公共配置表nf）")
    private Integer hpnarea;

    @ApiModelProperty(value = "经纬度")
    private Gis xy;

    @ApiModelProperty(value = "事件描述")
    private String evtdesc;


    @ApiModelProperty(value = "事件状态 0:待处置 1：处置中2：善后中 3：已关闭")
    private Integer evtst;

    @ApiModelProperty(value = "事件状态")
    private String  state;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "事发时间")
    private Date hpdt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上报时间")
    private Date rptdt;

    @ApiModelProperty(value = "区域名称简称")
    private String sname;

    @ApiModelProperty(value = "事件类型名称")
    private String etypenm;

    @ApiModelProperty(value = "事件等级名称")
    private String elevelnm;

    @ApiModelProperty(value = "事件等级颜色")
    private String color;

}
