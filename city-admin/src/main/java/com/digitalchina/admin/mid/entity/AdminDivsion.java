package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 行政区域划分
 */
@Data
@TableName("warn.warn_admdiv")
@ApiModel(value = "Admdiv对象", description = "行政区划")
public class AdminDivsion implements Serializable {

    @ApiModelProperty(value = "区划ID")
    @TableId("adid")
    private Integer adid;

    @ApiModelProperty(value = "父级区划ID")
    private Integer adpid;

    @ApiModelProperty(value = "区划名称" ,required = true)
    private String adnm;

    @ApiModelProperty(value = "区划全称" ,required = true)
    private String adflnm;

    @ApiModelProperty(value = "区划族谱")
    private Integer[] adupnms;

    @ApiModelProperty(value = "区划级别", required = true)
    private Integer adlev;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @TableField(exist = false)
    private AdminDivsion parent;
    @TableField(exist = false)
    private List<AdminDivsion> children; // 直接子分类

    public AdminDivsion getParent() {
        return parent;
    }

    public void setParent(AdminDivsion parent) {
        this.parent = parent;
    }

    public List<AdminDivsion> getChildren() {
        return children;
    }

    public void setChildren(List<AdminDivsion> children) {
        this.children = children;
    }

    public Integer getAdid() {
        return adid;
    }

    public void setAdid(Integer adid) {
        this.adid = adid;
    }

    public Integer getAdpid() {
        return adpid;
    }

    public void setAdpid(Integer adpid) {
        this.adpid = adpid;
    }

    public String getAdnm() {
        return adnm;
    }

    public void setAdnm(String adnm) {
        this.adnm = adnm;
    }

    public String getAdflnm() {
        return adflnm;
    }

    public void setAdflnm(String adflnm) {
        this.adflnm = adflnm;
    }

    public Integer[] getAdupnms() {
        return adupnms;
    }

    public void setAdupnms(Integer[] adupnms) {
        this.adupnms = adupnms;
    }

    public Integer getAdlev() {
        return adlev;
    }

    public void setAdlev(Integer adlev) {
        this.adlev = adlev;
    }

    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }

    public Date getModt() {
        return modt;
    }

    public void setModt(Date modt) {
        this.modt = modt;
    }
}
