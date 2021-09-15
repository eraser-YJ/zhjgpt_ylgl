package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 排水流量表
 * SDE.SZSS_PSLL
 * @author shkstart
 * @create 2020-08-25 10:21
 */
@ApiModel("排水流量表")
@Data
public class DrainageFlow {

    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private String ssll;//瞬时流量

    private String zll;//总流量

    private String ls;//流速

    private Integer sw;//水位

    private String gwll;//管网流量

    private String voltage;//电压

    private String tm;//时间
}
