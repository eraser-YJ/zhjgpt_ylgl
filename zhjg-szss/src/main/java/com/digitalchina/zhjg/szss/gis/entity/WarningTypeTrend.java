package com.digitalchina.zhjg.szss.gis.entity;

/**
 * 预警趋势实体
 */
public class WarningTypeTrend {
    private Integer yj;
    private Integer bj;
    private Integer yc;
    private Integer zc;
    private Integer trend;

    public Integer getTrend() {
        if("".equals(trend)||trend==null){
            return 0;//去除该属性的前后空格并进行非空非null判断
        }
        return trend;
    }

    public void setTrend(Integer trend) {
        this.trend = trend;
    }

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
