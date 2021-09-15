package com.digitalchina.zhjg.szss.gis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

@TableName("SDE.SZSS_PSD")
@ApiModel("排水站点基本信息")
public class DrainagePoint implements Serializable {
    private Integer objectId;//编号

    private String zdbh;//站点编号

    private String zdmc;//站点名称

    private String szwz;//所在位置

    private String szqy;//所在区域

    private String sp;//视频

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;//创建时间

    private Integer qydm;//区域代码

    private String enclosure; //附件/图片

    private String fzr;//负责人

    private String lxdh;//联系电话

    private String zrdw;//责任单位

    private Double jssd; //积水深度

    private Double ll; //实时流量

    private String yjdj; //预警等级

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date jcsj; //监测时间

    public void setQydm(Integer qydm) {
        this.qydm = qydm;
    }

    public Double getLl() {
        return ll;
    }

    public void setLl(Double ll) {
        this.ll = ll;
    }

    public String getYjdj() {
        return yjdj;
    }

    public void setYjdj(String yjdj) {
        this.yjdj = yjdj;
    }

    public Date getJcsj() {
        return jcsj;
    }

    public void setJcsj(Date jcsj) {
        this.jcsj = jcsj;
    }

    public Double getJssd() {
        return jssd;
    }

    public void setJssd(Double jssd) {
        this.jssd = jssd;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
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

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

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

    public String getSzqy() {
        return szqy;
    }

    public void setSzqy(String szqy) {
        this.szqy = szqy;
    }

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getQydm() {
        return qydm;
    }
}
