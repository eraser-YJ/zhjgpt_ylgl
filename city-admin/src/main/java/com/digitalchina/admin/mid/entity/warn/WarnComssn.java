package com.digitalchina.admin.mid.entity.warn;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 委办局
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-25
 */
@TableName("warn.warn_comssn")
@ApiModel(value="WarnComssn对象", description="委办局")
public class WarnComssn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "委办局ID")
    private Integer cmnid;

    @ApiModelProperty(value = "委办局名称")
    private String cmnnm;

    @ApiModelProperty(value = "委办局原名称")
    private String cmnnm2;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public Integer getCmnid() {
        return cmnid;
    }

    public void setCmnid(Integer cmnid) {
        this.cmnid = cmnid;
    }
    public String getCmnnm() {
        return cmnnm;
    }

    public void setCmnnm(String cmnnm) {
        this.cmnnm = cmnnm;
    }
    public String getCmnnm2() {
        return cmnnm2;
    }

    public void setCmnnm2(String cmnnm2) {
        this.cmnnm2 = cmnnm2;
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

    public static final String CMNID = "cmnid";

    public static final String CMNNM = "cmnnm";

    public static final String CMNNM2 = "cmnnm2";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "WarnComssn{" +
        "cmnid=" + cmnid +
        ", cmnnm=" + cmnnm +
        ", cmnnm2=" + cmnnm2 +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
