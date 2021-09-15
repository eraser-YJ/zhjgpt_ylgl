package com.digitalchina.admin.mid.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 委办局
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_comssn")
@ApiModel(value="Comssn对象", description="委办局")
public class Comssn implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "委办局ID")
    @TableId("cmnid")
    private Integer cmnid;

    @ApiModelProperty(value = "委办局名称", required = true)
    private String cmnnm;

    @ApiModelProperty(value = "委办局原名称", required = true)
    private String cmnnm2;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public void setCrdt(String crdt) {
        this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
    }

    public void setModt(String modt) {
        this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
    }

    public static final String CMNID = "cmnid";

    public static final String CMNNM = "cmnnm";

    public static final String CMNNM2 = "cmnnm2";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Comssn{" +
        "cmnid=" + cmnid +
        ", cmnnm=" + cmnnm +
        ", cmnnm2=" + cmnnm2 +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
