package com.digitalchina.zhjg.szss.gis.entity;

import io.swagger.models.auth.In;

public class WarningType {
    private Integer yj;
    private Integer bj;
    private Integer yc;

    private Integer zc;

    public Integer getZc() {
        if("".equals(zc)||zc==null){
            return 0;//去除该属性的前后空格并进行非空非null判断
        }
        return zc;
    }

    public void setZc(Integer zc) {
        this.zc = zc;
    }

    public Integer getYj() {
        if("".equals(yj)||yj==null){
            return 0;//去除该属性的前后空格并进行非空非null判断
        }
        return yj;
    }

    public void setYj(Integer yj) {
        this.yj = yj;
    }

    public Integer getBj() {
        if("".equals(bj)||bj==null){
            return 0;//去除该属性的前后空格并进行非空非null判断
        }
        return bj;
    }

    public void setBj(Integer bj) {
        this.bj = bj;
    }

    public Integer getYc() {
        if("".equals(yc)||yc==null){
            return 0;//去除该属性的前后空格并进行非空非null判断
        }
        return yc;
    }

    public void setYc(Integer yc) {
        this.yc = yc;
    }
}
