package com.digitalchina.zhjg.szss.gis.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel("供热上报数据表")
@Data
public class HotUpData {

    private String mc; //锅炉房名称

    private String yxqk; //锅炉房状态

    private Integer enterId; //公司id

    private Integer userId; //用户id

    private String userName; //用户名称

    private String status; //用户状态

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String uptime; //日志上报时间

    private String explain; //事件说明

    private String lxr;// 联系人

    private String lxphone; //联系电话

    private Integer glfId; //锅炉房id

    private String enName; //公司名称

    private String pic; //图片

}
