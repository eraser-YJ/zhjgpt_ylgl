package com.digitalchina.zhjg.szss.gis.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author shkstart
 * @create 2020-11-02 11:42
 */
@ApiModel("绿地信息管理")
@Data
public class GyldldDto {

    private String code;//	CODE 绿地唯一识别码

    private String name;//	NAME 绿地名称

    private Integer cateLevel1Id;//	CATE_LEVEL_1_ID	绿地一级类别编号

    private String cateLevel1Name;// CATE_LEVEL_1_NAME	绿地一级类别名称

    private Integer cateLevel2Id;//	CATE_LEVEL_2_ID

    private String cateLevel2Name;// CATE_LEVEL_2_NAME

    private Integer cateLevel3Id;//	CATE_LEVEL_3_ID

    private String cateLevel3Name;// CATE_LEVEL_3_NAME

    private Integer propertyCode;//	PROPERTY_CODE 绿地性质code

    private String propertyName;//	PROPERTY_NAME 绿地性质name

    private Integer degreeCode;// DEGREE_CODE 绿地等级

    private String degreeName;// DEGREE_NAME

    private Integer admDivCode;// ADMDIV_CODE 区域代码

    private String admDivName;// ADMDIV_NAME 所属区域

    private String area;//	AREA 绿化面积

    private Integer mainTypeCode;//	MAINTYPE_CODE 养护类别

    private String mainTypeName;//	MAINTYPE_NAME

    private Integer mainTainCode;//	MAINTAIN_CODE 养护单位

    private String mainTainName;//	MAINTAIN_NAME

    private String principal;// PRINCIPAL 责任人

    private String principalTel;//	PRINCIPAL_TEL 责任人电话

    private Date datecreated;//	DATE_CREATED 入库日期

    private Date dateBuild;// DATE_BUILD	建设时间

}
