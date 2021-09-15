package com.digitalchina.event.mid.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.digitalchina.event.utils.PgGisTypeHandler;

/**
 * <p>
 * 业务事件
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.busievent")
@ApiModel(value="Busievent对象", description="业务事件")
@NoArgsConstructor
@AllArgsConstructor
public class Busievent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件ID")
    @TableId("beid")
    private Integer beid;

    @ApiModelProperty(value = "流程ID")
    private Integer wfid;

    @ApiModelProperty(value = "事项来源代码")
    private Integer efbh;

    @ApiModelProperty(value = "事项类型代码")
    private Integer etbh;

    @ApiModelProperty(value = "区划ID")
    private Integer adid;

    @ApiModelProperty(value = "流程实例编号")
    private String procInstId;

    @ApiModelProperty(value = "事件编号")
    private String bebh;

    @ApiModelProperty(value = "事件名称")
    private String benm;

    @ApiModelProperty(value = "事件描述")
    private String becnt;

    @ApiModelProperty(value = "来源部门")
    private Integer besrcdpt;

    @ApiModelProperty(value = "指定承办部门")
    private Integer bepcdpt0;

    @ApiModelProperty(value = "最后承办部门")
    private Integer bepcdpt;

    @ApiModelProperty(value = "经派机构")
    private Integer allodpt;

    @ApiModelProperty(value = "转派时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date allodt;

    @ApiModelProperty(value = "受理部门")
    private Integer recdpt;

    @ApiModelProperty(value = "受理时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date recdt;

    @ApiModelProperty(value = "经手部门SET")
    private Integer bedpts;

    @ApiModelProperty(value = "场所(0室内，1室外，2无)")
    private Integer inroom;

    @ApiModelProperty(value = "地址")
    private String addr;

    /**
     * 坐标对象,需要转换成数据库坐标对象和大地坐标系, 隐式在typeHandler和sqlHandler中执行 不需要关注这块逻辑
     * ps:不同地图采用坐标系不同,需要对应
     *
     * @see PgGisTypeHandler
     */
    @TableField(value = "ST_AsGeoJSON  ( st_transform(bexy, 4326 ) ) :: JSON")
    @ApiModelProperty(value = "经纬度")
    private Gis bexy;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "联系人电话")
    private String linktel;

    @ApiModelProperty(value = "发生TS")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date hapndt;

    @ApiModelProperty(value = "转入时TS")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date indt;

    @ApiModelProperty(value = "转入时原编号")
    private String inoldbh;

    @ApiModelProperty(value = "事件状态(0待处理，内部处理中【1一级分拨，2二级分拨，10业务部门，】，应急处理中【20】，核查中【100一级分拨，101二级分拨】，1000关闭（办结）)")
    private Integer bestat;

    @ApiModelProperty(value = "总时长")
    private Integer alldura;

    @ApiModelProperty(value = "处置时长")
    private Integer pcdura;

    @ApiModelProperty(value = "关闭原因")
    private Integer clsrsn;

    @ApiModelProperty(value = "签收超时")
    private Integer acptmo;

    @ApiModelProperty(value = "开始处置超时")
    private Integer pcbgndtmo;

    @ApiModelProperty(value = "结束处置超时")
    private Integer pcendtmo;

    @ApiModelProperty(value = "办结超时")
    private Integer endtmo;

    @ApiModelProperty(value = "流程有超时")
    private Integer hastmo;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modt;

    @ApiModelProperty(value = "关闭时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date clsdt;

    @ApiModelProperty(value = "上升应急时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date upemgdt;

    @ApiModelProperty(value = "应急反馈时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date emgdowndt;

    @ApiModelProperty(value = "签收期限")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date acptdln;

    @ApiModelProperty(value = "预警期限")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date warndln;

    @ApiModelProperty(value = "开始处置期限")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pcbgndln;

    @ApiModelProperty(value = "结束处置期限")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pcendln;

    @ApiModelProperty(value = "办结期限")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endln;

    @ApiModelProperty(value = "乡镇ID")
    private Integer townAdid;

    @TableField(exist = false)
    @ApiModelProperty(value = "事件类型名称")
    private String etbhName;

    @TableField(exist = false)
    @ApiModelProperty(value = "事件来源名称")
    private String efbhName;

    @TableField(exist = false)
    @ApiModelProperty(value = "主要责任部门")
    private String besrcdpt0Name;

    @TableField(exist = false)
    @ApiModelProperty("序号")
    private Integer seq;
    
    @ApiModelProperty(value = "性质分类")
    private Integer type;

    public static final String BEID = "beid";

    public static final String WFID = "wfid";

    public static final String EFBH = "efbh";

    public static final String ETBH = "etbh";

    public static final String ADID = "adid";

    public static final String PROC_INST_ID = "proc_inst_id";

    public static final String BEBH = "bebh";

    public static final String BENM = "benm";

    public static final String BECNT = "becnt";

    public static final String BESRCDPT = "besrcdpt";

    public static final String BEPCDPT0 = "bepcdpt0";

    public static final String ALLODPT = "allodpt";

    public static final String RECDPT = "recdpt";

    public static final String BEPCDPT = "bepcdpt";

    public static final String BEDPTS = "bedpts";

    public static final String INROOM = "inroom";

    public static final String ADDR = "addr";

    public static final String BEXY = "bexy";

    public static final String LINKMAN = "linkman";

    public static final String LINKTEL = "linktel";

    public static final String HAPNDT = "hapndt";

    public static final String INDT = "indt";

    public static final String INOLDBH = "inoldbh";

    public static final String BESTAT = "bestat";

    public static final String ALLDURA = "alldura";

    public static final String PCDURA = "pcdura";

    public static final String CLSRSN = "clsrsn";

    public static final String ACPTMO = "acptmo";

    public static final String PCBGNDTMO = "pcbgndtmo";

    public static final String PCENDTMO = "pcendtmo";

    public static final String ENDTMO = "endtmo";

    public static final String HASTMO = "hastmo";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String CLSDT = "clsdt";

    public static final String UPEMGDT = "upemgdt";

    public static final String EMGDOWNDT = "emgdowndt";

    public static final String ACPTDLN = "acptdln";

    public static final String WARNDLN = "warndln";

    public static final String PCBGNDLN = "pcbgndln";

    public static final String PCENDLN = "pcendln";

    public static final String ENDLN = "endln";

    public static final String TOWN_ADIN = "town_adid";

    @Override
    public String toString() {
        return "Busievent{" +
                "beid=" + beid +
                ", wfid=" + wfid +
                ", efbh=" + efbh +
                ", etbh=" + etbh +
                ", adid=" + adid +
                ", procInstId=" + procInstId +
                ", bebh=" + bebh +
                ", benm=" + benm +
                ", becnt=" + becnt +
                ", besrcdpt=" + besrcdpt +
                ", bepcdpt0=" + bepcdpt0 +
                ", bepcdpt=" + bepcdpt +
                ", bedpts=" + bedpts +
                ", inroom=" + inroom +
                ", addr=" + addr +
                ", bexy=" + bexy +
                ", linkman=" + linkman +
                ", linktel=" + linktel +
                ", hapndt=" + hapndt +
                ", indt=" + indt +
                ", inoldbh=" + inoldbh +
                ", bestat=" + bestat +
                ", alldura=" + alldura +
                ", pcdura=" + pcdura +
                ", clsrsn=" + clsrsn +
                ", acptmo=" + acptmo +
                ", pcbgndtmo=" + pcbgndtmo +
                ", pcendtmo=" + pcendtmo +
                ", endtmo=" + endtmo +
                ", hastmo=" + hastmo +
                ", crdt=" + crdt +
                ", modt=" + modt +
                ", clsdt=" + clsdt +
                ", upemgdt=" + upemgdt +
                ", emgdowndt=" + emgdowndt +
                ", acptdln=" + acptdln +
                ", warndln=" + warndln +
                ", pcbgndln=" + pcbgndln +
                ", pcendln=" + pcendln +
                ", endln=" + endln +
                ", townAdid=" + townAdid +
                "}";
    }
}
