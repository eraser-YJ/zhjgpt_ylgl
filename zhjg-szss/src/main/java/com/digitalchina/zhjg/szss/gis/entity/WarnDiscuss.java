package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 预警研判实体
 */
@TableName("SDE.warn_discuss_decide")
@ApiModel("部件预警信息")
public class WarnDiscuss implements Serializable {
    private Integer id;
    private Integer partsWarnId;
    private String descriptInfo;
    private Date reportTime;
    private String picture;
    private String warningSource;

    @TableField(exist = false)
    private Integer warningStatusId; //预警状态id-关联sys_dict_item的主键id
    @TableField(exist = false)
    private Integer warningTypeId; //预警类型id-关联sys_dict_item的主键id
    @TableField(exist = false)
    private String warningStatusValue; //预警状态的name--对应sys_dict_item的item_name
    @TableField(exist = false)
    private String warningTypeValue; // 预警类型的name--对应sys_dict_item的item_name

    @TableField(exist = false)
    private Integer statusCode; //部件状态码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String statusName; //部件状态

    @TableField(exist = false)
    private Integer currencyCode; //部件现势性编码，对应pgsql->city-admin-mid::public.sys_dict_item.id

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Integer currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    @TableField(exist = false)
    private String currencyName; // 现势性


    public Integer getWarningStatusId() {
        return warningStatusId;
    }

    public void setWarningStatusId(Integer warningStatusId) {
        this.warningStatusId = warningStatusId;
    }

    public Integer getWarningTypeId() {
        return warningTypeId;
    }

    public void setWarningTypeId(Integer warningTypeId) {
        this.warningTypeId = warningTypeId;
    }

    public String getWarningStatusValue() {
        return warningStatusValue;
    }

    public void setWarningStatusValue(String warningStatusValue) {
        this.warningStatusValue = warningStatusValue;
    }

    public String getWarningTypeValue() {
        return warningTypeValue;
    }

    public void setWarningTypeValue(String warningTypeValue) {
        this.warningTypeValue = warningTypeValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartsWarnId() {
        return partsWarnId;
    }

    public void setPartsWarnId(Integer partsWarnId) {
        this.partsWarnId = partsWarnId;
    }

    public String getDescriptInfo() {
        return descriptInfo;
    }

    public void setDescriptInfo(String descriptInfo) {
        this.descriptInfo = descriptInfo;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getWarningSource() {
        return warningSource;
    }

    public void setWarningSource(String warningSource) {
        this.warningSource = warningSource;
    }
}
