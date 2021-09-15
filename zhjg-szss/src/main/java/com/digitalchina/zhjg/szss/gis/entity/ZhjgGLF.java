package com.digitalchina.zhjg.szss.gis.entity;

import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * 锅炉房实体类
 */
@Data
public class ZhjgGLF {
    private Integer objectId; //id
    private Integer glfbh; //锅炉房id(锅炉房编号)
    private String glfmc; //锅炉房名称
    private String xzxq; //行政区划名称
    private String glqy; //供热企业
    private Integer grnl; //供热能力
    private String glfxz;  //锅炉房性质
    private Integer zwmj; //在网面积
    private Integer jmyhs; //居民用户数
    private Integer xzxq_code; //行政区划编号
    private String xzqh;  //行政区划名称
    private Integer zdmj; //占地面积
    private String sfjc; //是否监测
    private String lxr; //联系人
    private String lxdh; //联系电话
    private String ksmj; //开栓面积
    private Integer fjmhs; //非居民用户数
    private String lds; //楼栋数
    private Integer hrzsl; //换热站数量
    private Integer glfzts;  //锅炉房总台数
    private String grdx; //供热队象
    private String glfdz; //锅炉房地址
    private String grfgfw; //锅炉房覆盖范围
    private String glfjj; //锅炉房简介
    private Date updatetime; //入库时间



}
