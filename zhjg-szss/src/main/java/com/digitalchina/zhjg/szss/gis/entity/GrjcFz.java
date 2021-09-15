package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 供热监测阀值设置表
 */
@TableName("SDE.SZSS_GRJC_FZ")
@ApiModel("供热监测阀值设置")
@Data
public class GrjcFz {
    private Integer objectId;
    private String heatSourceType;  //热源类型: hrz-换热站; glf-锅炉房; gdcwd-固定测温点
    private String tempeType;       //温度类型: 一次回水温度;二次回水温度
    private String fzlx;            //阀值类型:大于; 大于等于;等于;小于;小于等于
    private Double fzsh;            //阀值类型
    private String status;          //是否启用 : 0启用; 1禁用
    private String remark;          //备注
}
