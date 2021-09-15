package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 积水点设备信息表
 * @author shkstart
 * @create 2020-08-20 11:02
 */
@TableName("SDE.SZSS_JSDDEVICE")
@ApiModel("积水点设备信息表")
@Data
public class PondingPointDevice {


    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String sbbh;//设备编号

    private String sbmc;//设备名称

    private String sblx;//设备类型

    private String txzt;//通讯状态

    private String sbzt;//设备状态

    private String zdmc;//站点名称

    private String szwz;//所在位置

    private Date zhcjsj;//最后采集时间

    private String bjfz;//报警阀值

    private String cjsjjg;//采集时间间隔

    private Date createtime;//创建时间

    private String szqy; //所在区域

    private String qydm;//区域代码

    private String csxx;//参数信息

    private String picture;//图片

    private String sblxbh; //设备分类代码
}


