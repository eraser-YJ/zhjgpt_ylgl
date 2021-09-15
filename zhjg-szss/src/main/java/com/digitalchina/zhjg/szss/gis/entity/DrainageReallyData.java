package com.digitalchina.zhjg.szss.gis.entity;

import java.util.Date;

/**
 * 排水点实时数据实体类
 */

public class DrainageReallyData {


    private Double q; //瞬时流量

    private Double acc_w; //总流量

    private Double v; //流速

    private String stcd; //测站编码

    private Double z; //水位

    private String wptn;//水势

    private Double voltage; //电压

    private Date tm; //时间

    private Integer id; //id

    private String zdmc; //站点名称

    private String zrdw; //责任单位

    private String fzr; //负责人

    private String szwz; //所在位置

    private String szqy; //所在区域

    private String lxdh; //联系电话

    private String yjdj; //预警等级

    public String getYjdj() {
        return yjdj;
    }

    public void setYjdj(String yjdj) {
        this.yjdj = yjdj;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getZrdw() {
        return zrdw;
    }

    public void setZrdw(String zrdw) {
        this.zrdw = zrdw;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz;
    }

    public String getSzqy() {
        return szqy;
    }

    public void setSzqy(String szqy) {
        this.szqy = szqy;
    }

    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTm() {
        return tm;
    }

    public void setTm(Date tm) {
        this.tm = tm;
    }

    public Double getQ() {
        return q;
    }

    public void setQ(Double q) {
        this.q = q;
    }

    public Double getAcc_w() {
        return acc_w;
    }

    public void setAcc_w(Double acc_w) {
        this.acc_w = acc_w;
    }

    public Double getV() {
        return v;
    }

    public void setV(Double v) {
        this.v = v;
    }

    public String getStcd() {
        return stcd;
    }

    public void setStcd(String stcd) {
        this.stcd = stcd;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public String getWptn() {
        return wptn;
    }

    public void setWptn(String wptn) {
        this.wptn = wptn;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }
}
