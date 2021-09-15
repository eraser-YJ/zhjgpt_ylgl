package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 排水积水设备异常处置表--实体
 */
@TableName("SDE.SZSS_DEVICE_HANDLE")
@ApiModel("排水积水设备异常处置表")
@Data
public class DeviceWarnHandleParam {
    private Integer objectId; //编号

    private String zdbh; //站点编号

    private String zdmc; //站点名称

    private String yjjb; //预警级别

    private String yjlx; //预警类型

    private String sbmc; //设备名称

    private String sbbh; //设备编号

    private String status; //处置状态

    private String jjcd; //紧急程度

    private String fzr; //负责人

    private String lxdh; //联系电话

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime; //创建时间

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String zxclsj; //最新处理时间

    private String deviceWarnId; //关联SZSS_DEVICE_WARNING中OBJECTID

    private String czdw; //处置单位

    private String cznr; //处置内容

    private String dxnr; //短信内容

    private String lednr; //LED内容

    private String yjcz; //预警处置

    private String sblx; //设备类型

    private String sbzt; //设备状态

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
