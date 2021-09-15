package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 积水点实时信息采集表
 * SDE.SZSS_JSD_REALDATA
 * @author shkstart
 * @create 2020-08-19 20:04
 */
@ApiModel("积水点实时信息采集表")
@Data
public class PondingPointRealData {

    private Integer objectid;//编号

    private String zdbh;//站点编号

    private Double jssd;//积水深度

    private Double jsmj;//积水面积

    private Double jyl;//降雨量

    private String jyjb;//降雨级别

    private String yjdj;//预警等级/积水登记

    private Double yjz;//预警值

    private String ly;//来源

    private Date jcsj;//监测时间
}
