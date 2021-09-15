package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 物联网数据分时汇总
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Data
@TableName("warn.warn_iotsbh")
@ApiModel(value="Iotsbh对象", description="物联网数据分时汇总")
public class Iotsbh implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "2019123123", required = true)
    private Integer ymdh;

    @ApiModelProperty(value = "年月日")
    private Integer ymd;

    @ApiModelProperty(value = "年月")
    private Integer ym;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "测试数")
    private Integer testn;

    @ApiModelProperty(value = "正常数")
    private Integer normn;

    @ApiModelProperty(value = "预警数")
    private Integer warnn;

    @ApiModelProperty(value = "报警数")
    private Integer faultn;

    @ApiModelProperty(value = "故障数")
    private Integer errn;

    @ApiModelProperty(value = "待处理数")
    private Integer wfptn;

    @ApiModelProperty(value = "处理中数")
    private Integer inpn;

    @ApiModelProperty(value = "处理完数")
    private Integer fnpn;

    @ApiModelProperty(value = "非本时段处理中数")
    private Integer inpn2;

    @ApiModelProperty(value = "非本时段处理完数")
    private Integer fnpn2;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public Integer getYmdh() {
        return ymdh;
    }

    public void setYmdh(Integer ymdh) {
        this.ymdh = ymdh;
    }
    public Integer getYmd() {
        return ymd;
    }

    public void setYmd(Integer ymd) {
        this.ymd = ymd;
    }
    public Integer getYm() {
        return ym;
    }

    public void setYm(Integer ym) {
        this.ym = ym;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getTestn() {
        return testn;
    }

    public void setTestn(Integer testn) {
        this.testn = testn;
    }
    public Integer getNormn() {
        return normn;
    }

    public void setNormn(Integer normn) {
        this.normn = normn;
    }
    public Integer getWarnn() {
        return warnn;
    }

    public void setWarnn(Integer warnn) {
        this.warnn = warnn;
    }
    public Integer getFaultn() {
        return faultn;
    }

    public void setFaultn(Integer faultn) {
        this.faultn = faultn;
    }
    public Integer getErrn() {
        return errn;
    }

    public void setErrn(Integer errn) {
        this.errn = errn;
    }
    public Integer getWfptn() {
        return wfptn;
    }

    public void setWfptn(Integer wfptn) {
        this.wfptn = wfptn;
    }
    public Integer getInpn() {
        return inpn;
    }

    public void setInpn(Integer inpn) {
        this.inpn = inpn;
    }
    public Integer getFnpn() {
        return fnpn;
    }

    public void setFnpn(Integer fnpn) {
        this.fnpn = fnpn;
    }
    public Integer getInpn2() {
        return inpn2;
    }

    public void setInpn2(Integer inpn2) {
        this.inpn2 = inpn2;
    }
    public Integer getFnpn2() {
        return fnpn2;
    }

    public void setFnpn2(Integer fnpn2) {
        this.fnpn2 = fnpn2;
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

    public static final String YMDH = "ymdh";

    public static final String YMD = "ymd";

    public static final String YM = "ym";

    public static final String YEAR = "year";

    public static final String TESTN = "testn";

    public static final String NORMN = "normn";

    public static final String WARNN = "warnn";

    public static final String FAULTN = "faultn";

    public static final String ERRN = "errn";

    public static final String WFPTN = "wfptn";

    public static final String INPN = "inpn";

    public static final String FNPN = "fnpn";

    public static final String INPN2 = "inpn2";

    public static final String FNPN2 = "fnpn2";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Iotsbh{" +
        "ymdh=" + ymdh +
        ", ymd=" + ymd +
        ", ym=" + ym +
        ", year=" + year +
        ", testn=" + testn +
        ", normn=" + normn +
        ", warnn=" + warnn +
        ", faultn=" + faultn +
        ", errn=" + errn +
        ", wfptn=" + wfptn +
        ", inpn=" + inpn +
        ", fnpn=" + fnpn +
        ", inpn2=" + inpn2 +
        ", fnpn2=" + fnpn2 +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
