package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * 积水点基本信息表
 * SDE.SZSS_JSD
 * @author shkstart
 * @create 2020-08-19 19:56
 */
@ApiModel("积水点基本信息表")
@Data
public class PondingPoint {

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

    private Integer qydm;//区域代码
}
