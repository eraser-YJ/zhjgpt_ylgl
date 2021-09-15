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
 * 组合属性
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_cpsprop")
@ApiModel(value="Cpsprop对象", description="组合属性")
public class Cpsprop implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组合属性ID")
    @TableId("cpid")
    private Integer cpid;

    @ApiModelProperty(value = "组合属性名称")
    private String cpnm;

    @ApiModelProperty(value = "属性数")
    private Integer cpnum;

    @ApiModelProperty(value = "禁用启用（-1禁用，0启用但不处理，1启用且处理）", required = true)
    private Integer stat;

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

    public static final String CPID = "cpid";

    public static final String CPNM = "cpnm";

    public static final String CPNUM = "cpnum";

    public static final String STAT = "stat";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Cpsprop{" +
        "cpid=" + cpid +
        ", cpnm=" + cpnm +
        ", cpnum=" + cpnum +
        ", stat=" + stat +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
