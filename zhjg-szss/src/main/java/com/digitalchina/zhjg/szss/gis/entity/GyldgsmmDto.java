package com.digitalchina.zhjg.szss.gis.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("古树名木管理")
@Data
public class GyldgsmmDto {
    private String code;//	CODE 树木唯一编码

    private Integer ldObjectId;// LD_OBJECTID	所属绿地

    private Integer zwpzCode;//	ZWPZ_CODE 树种编号

    private String zwpz;//	ZWPZ 树种

    private Integer sg;// SG 树高

    private Integer gf;// GF 冠幅

    private Integer xj;// XJ 胸径

    private Integer fzd;// FZD 分支点

    private Integer sl;// SL 树龄

    private Integer szzkCode;//	SZZK_CODE 生长状况编号

    private String szzk;//	SZZK 生长状况

    private Integer yhdjCode;//	YHDJ_CODE 养护等级编号

    private String yhdj;//	YHDJ 养护等级

    private Integer xzqhCode;//	XZQH_CODE 行政区域编号

    private String xzqh;//	XZQH 行政区域

    private String principal;// PRINCIPAL 责任人

    private String principalTel;//	PRINCIPAL_TEL 责任人电话

    private String remark;// REMARK 备注

    private Date dateCreated;// DATE_CREATED	创建日期

}
