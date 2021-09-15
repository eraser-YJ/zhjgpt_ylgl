package com.digitalchina.event.mid.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 业务事件阶段
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.bestep")
@ApiModel(value="Bestep对象", description="业务事件阶段")
public class Bestep implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件阶段ID")
    @TableId("esid")
    private Integer esid;

    @ApiModelProperty(value = "流程ID")
    private Integer wfid;

    @ApiModelProperty(value = "事件ID")
    private Integer beid;

    @ApiModelProperty(value = "任务Key")
    private String tskey;

    @ApiModelProperty(value = "来件TS")
    private Date esrcvdt;

    @ApiModelProperty(value = "已读TS")
    private Date esreadt;

    @ApiModelProperty(value = "已完成TS")
    private Date esendt;

    @ApiModelProperty(value = "环节类型")
    private Integer estype;

    @ApiModelProperty(value = "阶段用时")
    private Integer esdura;

    @ApiModelProperty(value = "阶段状态")
    private Integer estat;

    @ApiModelProperty(value = "是否超时")
    private Integer estmout;

    @ApiModelProperty(value = "上游来源人")
    private Integer esupman;

    @ApiModelProperty(value = "上游来源部门")
    private Integer esupdept;

    @ApiModelProperty(value = "操作人")
    private Integer esopman;

    @ApiModelProperty(value = "操作部门")
    private Integer esopdept;

    @ApiModelProperty(value = "操作")
    private String esoper;

    @ApiModelProperty(value = "办理意见")
    private String esopn;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @ApiModelProperty(value = "指定承办部门")
    private Integer bepcdpt0;

    public static final String ESID = "esid";

    public static final String WFID = "wfid";

    public static final String BEID = "beid";

    public static final String TSKEY = "tskey";

    public static final String ESRCVDT = "esrcvdt";

    public static final String ESREADT = "esreadt";

    public static final String ESENDT = "esendt";

    public static final String ESTYPE = "estype";

    public static final String ESDURA = "esdura";

    public static final String ESTAT = "estat";

    public static final String ESTMOUT = "estmout";

    public static final String ESUPMAN = "esupman";

    public static final String ESUPDEPT = "esupdept";

    public static final String ESOPMAN = "esopman";

    public static final String ESOPDEPT = "esopdept";

    public static final String ESOPER = "esoper";

    public static final String ESOPN = "esopn";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String BEPCDPT0 = "bepcdpt0";

    @Override
    public String toString() {
        return "Bestep{" +
        "esid=" + esid +
        ", wfid=" + wfid +
        ", beid=" + beid +
        ", tskey=" + tskey +
        ", esrcvdt=" + esrcvdt +
        ", esreadt=" + esreadt +
        ", esendt=" + esendt +
        ", estype=" + estype +
        ", esdura=" + esdura +
        ", estat=" + estat +
        ", estmout=" + estmout +
        ", esupman=" + esupman +
        ", esupdept=" + esupdept +
        ", esopman=" + esopman +
        ", esopdept=" + esopdept +
        ", esoper=" + esoper +
        ", esopn=" + esopn +
        ", crdt=" + crdt +
        ", modt=" + modt +
        ", bepcdpt0=" + bepcdpt0 +
        "}";
    }
}
