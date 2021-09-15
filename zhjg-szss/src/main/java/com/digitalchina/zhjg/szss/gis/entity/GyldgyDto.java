package com.digitalchina.zhjg.szss.gis.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("公园信息管理")
@Data
public class GyldgyDto {
    private String code;  //CODE公园唯一识别号

    private String name; // NAME 公园名称

    private String degreeName; //DEGREE_NAME 公园等级

    private Integer degreeCode; //DEGREE_CODE 公园等级编号

    private String address; //ADDRESS 公演地址

    private String openlevelName; //OPENLEVEL_NAME 开放情况

    private Integer openlevelCode; //OPENLEVEL_CODE 开放情况编码

    private Integer totalArea; //TOTAL_AREA 公园总面积

    private Integer greenArea; //GREEN_AREA 绿地面积

    private Integer waterArea; //WATER_AREA 水域面积

    private String maintainName; //MAINTAIN_NAME 养护单位

    private Integer maintainCode; //MAINTAIN_CODE 养护单位编码

    private String principal; //PRINCIPAL 负责人

    private String principalTel; // PRINCIPAL_TEL 负责人电话

    private String xzqh; // XZQH 行政区划

    private Integer xzqhCode; //XZQH_CODE  行政区划编号

    private Date dateBuild; // DATE_BUILD 建设时间

    private Date datecreated;//	DATE_CREATED 入库日期

    private String remark; //REMARK 备注


}
