package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 排水水位表
 * SDE.SZSS_PSSW
 * @author shkstart
 * @create 2020-08-25 10:30
 */
@ApiModel("排水水位表")
@Data
public class DrainageLevel {

    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private Double sw;//水位

    private String wptn;//水势

    private String voltage;//电压

    private String tm;//接收时间

    private String cjsj;//采集时间

    private String sjll;//数据类型

    private Integer sxz;//上限值

    private Integer xxz;//下限值
}
