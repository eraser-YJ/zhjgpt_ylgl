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
 * 事实表物联网
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Data
@TableName("warn.warn_iotfact")
@ApiModel(value="Iotfact对象", description="事实表物联网")
public class Iotfact implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事实表ID")
    @TableId("ifid")
    private Integer ifid;

    @ApiModelProperty(value = "指标ID")
    private Integer msid;

    @ApiModelProperty(value = "设备ID")
    private Integer idid;

    @ApiModelProperty(value = "图层ID")
    private Integer lyid;

    @ApiModelProperty(value = "传感器ID")
    private Integer idsid;

    @ApiModelProperty(value = "坐标")
    private String wnxy;

    @ApiModelProperty(value = "次数")
    private Integer wnnum;

    @ApiModelProperty(value = "物联网数据产生的时间、业务数据对应的时间")
    private Date dgdt;

    @ApiModelProperty(value = "物联网数据产生的时间、业务数据对应的时间，不是TS的时候用")
    private String dgdt2;

    @ApiModelProperty(value = "非数值指标组")
    private String im2[];

    @ApiModelProperty(value = "数值指标组")
    private String im1[];

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    public void setCrdt(String crdt) {
        this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
    }

    public static final String IFID = "ifid";

    public static final String MSID = "msid";

    public static final String IDID = "idid";

    public static final String LYID = "lyid";

    public static final String IDSID = "idsid";

    public static final String WNXY = "wnxy";

    public static final String WNNUM = "wnnum";

    public static final String DGDT = "dgdt";

    public static final String DGDT2 = "dgdt2";

    public static final String IM2 = "im2";

    public static final String IM1 = "im1";

    public static final String CRDT = "crdt";

    @Override
    public String toString() {
        return "Iotfact{" +
        "ifid=" + ifid +
        ", msid=" + msid +
        ", idid=" + idid +
        ", lyid=" + lyid +
        ", idsid=" + idsid +
        ", wnxy=" + wnxy +
        ", wnnum=" + wnnum +
        ", dgdt=" + dgdt +
        ", dgdt2=" + dgdt2 +
        ", im2=" + im2 +
        ", im1=" + im1 +
        ", crdt=" + crdt +
        "}";
    }
}
