package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 组合属性清单
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_cpsplst")
@ApiModel(value="Cpsplst对象", description="组合属性清单")
public class Cpsplst implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性ID")
    @TableId("cplid")
    private Integer cplid;

    @ApiModelProperty(value = "组合属性ID")
    private Integer cpid;

    @ApiModelProperty(value = "属性名")
    private String cplnm;

    @ApiModelProperty(value = "必填？0-false 1-true")
    private Boolean mstfl;

    @ApiModelProperty(value = "顺序")
    private Integer ord;

    @ApiModelProperty(value = "-1禁用，0启用但不处理，1启用且处理", required = true)
    private Integer stat;

    public static final String CPLID = "cplid";

    public static final String CPID = "cpid";

    public static final String CPLNM = "cplnm";

    public static final String MSTFL = "mstfl";

    public static final String ORD = "ord";

    public static final String STAT = "stat";

    @Override
    public String toString() {
        return "Cpsplst{" +
        "cplid=" + cplid +
        ", cpid=" + cpid +
        ", cplnm=" + cplnm +
        ", mstfl=" + mstfl +
        ", ord=" + ord +
        ", stat=" + stat +
        "}";
    }
}
