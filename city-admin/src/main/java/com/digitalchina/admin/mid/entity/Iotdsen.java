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
 * 设备传感器(实时数据)
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Data
@TableName("warn.warn_iotdsen")
@ApiModel(value="Iotdsen对象", description="设备传感器(实时数据)")
public class Iotdsen implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "传感器名称")
    private String idsnm;

    @ApiModelProperty(value = "传感器ID")
    @TableId("idsid")
    private Integer idsid;

    @ApiModelProperty(value = "设备ID")
    private Integer idid;

    @ApiModelProperty(value = "指标ID")
    private Integer msid;

    @ApiModelProperty(value = "异常启用")
    private Boolean idexp;

    @ApiModelProperty(value = "1天多少次")
    private Integer iditv;

    @ApiModelProperty(value = "0正常，1异常")
    private Integer idstat;

    @ApiModelProperty(value = "留存规则")
    private String kptrl;

    @ApiModelProperty(value = "顺序")
    private Integer ord;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @ApiModelProperty(value = "传感器代码")
    private String idscode;


    public void setCrdt(String crdt) {
        this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
    }

    public void setModt(String modt) {
        this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
    }

    public static final String IDSNM = "idsnm";

    public static final String IDSID = "idsid";

    public static final String IDID = "idid";

    public static final String MSID = "msid";

    public static final String IDEXP = "idexp";

    public static final String IDITV = "iditv";

    public static final String IDSTAT = "idstat";

    public static final String KPTRL = "kptrl";

    public static final String ORD = "ord";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String IDSCODE = "idscode";
}
