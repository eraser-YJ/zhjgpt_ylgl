package com.digitalchina.zhjg.szss.gis.entity;

import lombok.Data;

/**
 * @author shkstart
 * @create 2020-12-17 18:10
 */
@Data
public class ZhjgRltj {

    private String nr_code;//行政区划名称

    private String glqy;//供热企业名称

    private String glfmc;//锅炉房名称

    private String hrzmc;//换热张名称

    private String subregion;//换热站分区

    private String 	ycgswd	;//	一次网供水温度

    private String 	ychswd	;//	一次网回水温度

    private String 	ycgsyl	;//	一次网供水压力

    private String 	ychsyl	;//	一次网回水压力

    private String 	ecgswd	;//	二次网供水温度

    private String 	echswd	;//	二次网回水温度

    private String 	ecgsyl	;//	二次网供水压力

    private String 	echsyl	;//	二次网回水压力



}
