package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author shkstart
 * @create 2020-08-27 11:13
 */
@Data
public class WarnHandleStatus {

    private Integer objectId;//编号

    private String cznr;//处置内容

    private String dxnr;//短信内容

    private String lednr;//LED内容

    private String status;//处置状态：0 开始 1 处置中 2关闭

    private String updateTime;//更新时间

    private String jcdlx;//监测点类型 0 积水， 1 排水

    private String sjbh;//事件编号

    private String tel;//手机号

    private String yjcz;//预警处置

    @TableField(exist = false)
    private String czr; //处置人
}
