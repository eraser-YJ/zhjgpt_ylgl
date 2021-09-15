package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@TableName("SDE.SZSS_DEVICE_HANDLE_STATUS")
@ApiModel("排水积水设备异常处置状态表")
@Data
public class DeviceWarnHandleStatus {
    private Integer objectId; //主键

    private String deviceWarnId; //关联SZSS_DEVICE_HANDLE中的OBJECTID

    private String cznr; //处置内容

    private String dxnr; //短信内容

    private String lednr; //LED内容

    private String status; //处置状态：0 开始 1 处置中 2关闭

    private String yjcz; //预警处置

    private String lxdh; //联系电话

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String updateTime; //更新时间
}
