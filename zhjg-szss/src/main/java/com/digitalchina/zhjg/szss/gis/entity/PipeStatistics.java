package com.digitalchina.zhjg.szss.gis.entity;

import lombok.Data;

@Data
public class PipeStatistics {

    private String cz; //材质

    private String len; //长度

    private Integer  xzqhCode; //行政区划代码

    private String code; //表名

    private String name; //管线名称

    private String xzqh; //行政区划
}
