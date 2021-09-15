package com.digitalchina.zhjg.szss.gis.entity;

import lombok.Data;

/**
 * 用于展示gis排水列表实体类
 */
@Data
public class DrainageGis {
    private Integer objectId; //id
    private String sw; //水位
    private String ls; //流速
    private String ll; //流量
    private String yjdj; //预警等级
    private String zdmc; //站点名称
    private String zdbh; //站点编号
    private String wkt; //排水点坐标
}
