package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class PartsInfo implements Serializable {
    @TableField(exist = false)
    private Integer warnId;
    @TableField(exist = false)
    private String configTb; //关联地信config表中TB字段--数据表名
    @TableField(exist = false)
    private Integer referId;  //关联数据表中的OBJECTID--部件信息id

    private Integer baseId;

    @TableField(exist = false)
    private String partsCateCode;  //部件分类编码，来源pgsql->city-admim-mid::szss.parts_category.code，也是实际对应的部件表名
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
    private Integer statusCode; //部件状态码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String statusName; //部件状态
    @TableField(exist = false)
    private Integer currencyCode; //部件现势性编码，对应pgsql->city-admin-mid::public.sys_dict_item.id
    @TableField(exist = false)
    private String currencyName; // 现势性
    @TableField(exist = false)
    private String wzms; //部件地址

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
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

    public String getWzms() {
        return wzms;
    }

    public void setWzms(String wzms) {
        this.wzms = wzms;
    }
}
