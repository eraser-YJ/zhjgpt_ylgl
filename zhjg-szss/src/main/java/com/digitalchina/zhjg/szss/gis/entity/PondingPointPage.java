package com.digitalchina.zhjg.szss.gis.entity;

import lombok.Data;

import java.util.Date;

/**
 * 积水点基本信息与实时采集数据结果集
 * @author shkstart
 * @create 2020-08-19 19:56
 */
@Data
public class PondingPointPage {

    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private String zrdw;//责任单位

    private String szwz;//所在位置

    private String szqy;//所在区域

    private String yc;//遥测

    private String sp;//视频

    private String fzr;//负责人

    private String lxdh;//联系电话

    private Date createtime;//创建时间

    private String jssd;//积水深度

    private String jsmj;//积水面积

    private String jyl;//降雨量

    private String jyjb;//降雨级别

    private String yjdj;//预警等级

    private String yjz;//预警值

    private String ly;//来源

    private Date jcsj;//监测时间


}
