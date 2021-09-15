package com.digitalchina.zhjg.szss.gis.entity;

import lombok.Data;

/**
 * 部件统计结果集
 * @author shkstart
 * @create 2020-08-11 14:35
 */
@Data
public class ZhjgBjtj {

    private String bjfl;//部件分类

    private String bjmc;//部件名称

    private String xqmc;//新区名称

    private String total;//总计

    private String bhNum;//北湖科技开发区 数量

    private String cdNum;//长德开发区 数量

    private String kgNum;//空港开发区 数量

    private String gxNum;//高新技术开发区 数量

}
