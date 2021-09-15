package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@TableName("SDE.SZSS_PSDDEVICE")
@ApiModel("排水设备信息")
public class DrainagePointDevice implements Serializable {
    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String sbbh;//设备编号

    private String sbmc;//设备名称

    private String sblx;//设备类型

    private String txzt;//通讯状态

    private String sbzt;//设备状态

    private String zdmc;//站点名称

    private String szwz;//所在位置

    private Date zhcjsj;//最后采集时间

    private String bjfz;//报警阀值

    private String cjsjjg;//采集时间间隔

    private Date createtime;//创建时间

    private String szqy; //所在区域

    private String qydm;//区域代码

    private String csxx;//参数信息

    private String picture;//图片

    private String sblxbh; //设备分类代码

    public Integer getObjectId() {
        return objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getZdbh() {
        return zdbh;
    }

    public void setZdbh(String zdbh) {
        this.zdbh = zdbh;
    }

    public String getSbbh() {
        return sbbh;
    }

    public void setSbbh(String sbbh) {
        this.sbbh = sbbh;
    }

    public String getSbmc() {
        return sbmc;
    }

    public void setSbmc(String sbmc) {
        this.sbmc = sbmc;
    }

    public String getSblx() {
        return sblx;
    }

    public void setSblx(String sblx) {
        this.sblx = sblx;
    }

    public String getTxzt() {
        return txzt;
    }

    public void setTxzt(String txzt) {
        this.txzt = txzt;
    }

    public String getSbzt() {
        return sbzt;
    }

    public void setSbzt(String sbzt) {
        this.sbzt = sbzt;
    }

    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz;
    }

    public Date getZhcjsj() {
        return zhcjsj;
    }

    public void setZhcjsj(Date zhcjsj) {
        this.zhcjsj = zhcjsj;
    }

    public String getBjfz() {
        return bjfz;
    }

    public void setBjfz(String bjfz) {
        this.bjfz = bjfz;
    }

    public String getCjsjjg() {
        return cjsjjg;
    }

    public void setCjsjjg(String cjsjjg) {
        this.cjsjjg = cjsjjg;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getSzqy() {
        return szqy;
    }

    public void setSzqy(String szqy) {
        this.szqy = szqy;
    }

    public String getQydm() {
        return qydm;
    }

    public void setQydm(String qydm) {
        this.qydm = qydm;
    }

    public String getCsxx() {
        return csxx;
    }

    public void setCsxx(String csxx) {
        this.csxx = csxx;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getSblxbh() {
        return sblxbh;
    }

    public void setSblxbh(String sblxbh) {
        this.sblxbh = sblxbh;
    }
}
