package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * 部件预警信息
 */
@TableName("SDE.parts_warn_info")
@ApiModel("部件预警信息")
public class PartsWarnInfo implements Serializable {

    private Integer warnId;
    private String configTb; //关联地信config表中TB字段--数据表名
    private Integer referId;  //关联数据表中的OBJECTID--部件信息id
    private Integer warningStatusId; //预警状态id-关联sys_dict_item的主键id
    private Integer warningTypeId; //预警类型id-关联sys_dict_item的主键id
    private String warningStatusValue; //预警状态的name--对应sys_dict_item的item_name
    private String warningTypeValue; // 预警类型的name--对应sys_dict_item的item_name
    private Date warningTime;  //预警时间

    @TableField(exist = false)
    private Integer baseId;
    @TableField(exist = false)
    private String startwarningTime; //开始时间
    @TableField(exist = false)
    private String endwarningTime; //结束时间

    @TableField(exist = false)
    private String partsCateCode;  //部件分类编码，来源pgsql->city-admim-mid::szss.parts_category.code，也是实际对应的部件表名

    @TableField(exist = false)
    private Integer partsCateId;

    @TableField(exist = false)
    private String partsCateName; //部件分类名，如“红绿灯”
    @TableField(exist = false)
    private Integer ownerCode ; //权属单位编码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String ownerName;  //权属单位
    @TableField(exist = false)
    private String  partsCode; //部件唯一编码
    @TableField(exist = false)
    private String partsName; //部件名称
    @TableField(exist = false)
    private Integer admdivCode; //行政区划，对应pgsql->city-admin-mid::warn.warn_admdiv.adid
    @TableField(exist = false)
    private String admdivName; //行政区划名
    @TableField(exist = false)
    private Integer statusCode; //部件状态码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String statusName; //部件状态
    @TableField(exist = false)
    private Integer currencyCode; //部件现势性编码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String currencyName; // 现势性

    private String partsCateTopName;

    public String getPartsCateTopName() {
        return partsCateTopName;
    }

    public void setPartsCateTopName(String partsCateTopName) {
        this.partsCateTopName = partsCateTopName;
    }

    @TableField(exist = false)
    private String wzms; //部件地址

    public String getStartwarningTime() {
        return startwarningTime;
    }

    public void setStartwarningTime(String startwarningTime) {
        this.startwarningTime = startwarningTime;
    }

    public String getEndwarningTime() {
        return endwarningTime;
    }

    public void setEndwarningTime(String endwarningTime) {
        this.endwarningTime = endwarningTime;
    }

    public String getWzms() {
        return wzms;
    }

    public void setWzms(String wzms) {
        this.wzms = wzms;
    }

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

    public Integer getAdmdivCode() {
        return admdivCode;
    }

    public void setAdmdivCode(Integer admdivCode) {
        this.admdivCode = admdivCode;
    }

    public String getAdmdivName() {
        return admdivName;
    }

    public void setAdmdivName(String admdivName) {
        this.admdivName = admdivName;
    }

    public Integer getWarnId() {
        return warnId;
    }

    public void setWarnId(Integer warnId) {
        this.warnId = warnId;
    }

    public String getConfigTb() {
        return configTb;
    }

    public void setConfigTb(String configTb) {
        this.configTb = configTb;
    }

    public Integer getReferId() {
        return referId;
    }

    public void setReferId(Integer referId) {
        this.referId = referId;
    }

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

    public Date getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(Date warningTime) {
        this.warningTime = warningTime;
    }

    public String getPartsCateCode() {
        return partsCateCode;
    }

    public void setPartsCateCode(String partsCateCode) {
        this.partsCateCode = partsCateCode;
    }

    public String getPartsCateName() {
        return partsCateName;
    }

    public void setPartsCateName(String partsCateName) {
        this.partsCateName = partsCateName;
    }

    public Integer getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPartsCode() {
        return partsCode;
    }

    public void setPartsCode(String partsCode) {
        this.partsCode = partsCode;
    }

    public String getPartsName() {
        return partsName;
    }

    public void setPartsName(String partsName) {
        this.partsName = partsName;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public Integer getPartsCateId() {
        return partsCateId;
    }

    public void setPartsCateId(Integer partsCateId) {
        this.partsCateId = partsCateId;
    }
}
