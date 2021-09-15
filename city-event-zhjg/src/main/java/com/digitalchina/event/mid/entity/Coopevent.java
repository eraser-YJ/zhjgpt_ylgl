package com.digitalchina.event.mid.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 协查事件
 * </p>
 *
 * @author lichunlong
 * @since 2019-09-15
 */
@ApiModel(value="Coopevent对象", description="协查事件")
@Data
public class Coopevent implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事件ID")
    @TableId("ceid")
    private Integer ceid;

    @ApiModelProperty(value = "流程实例ID")
    private String procInstId;

    @ApiModelProperty(value = "流程ID")
    private Integer wfid;

    @ApiModelProperty(value = "协查机构")
    private Integer cpbedid;

    @ApiModelProperty(value = "事件编号")
    private String cebh;

    @ApiModelProperty(value = "事件名称")
    private String cenm;

    @ApiModelProperty(value = "事件描述")
    private String cecnt;

    @ApiModelProperty(value = "事项类型代码")
    private Integer etbh;

    @ApiModelProperty(value = "发生TS")
    private Date hapndt;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "区划ID")
    private Integer adid;

    @ApiModelProperty(value = "事件状态")
    private Integer cestat;

    @ApiModelProperty(value = "乡镇ID")
    private Integer townAdid;

    @ApiModelProperty(value = "最后承办部门")
    private Integer cepcdpt;

    @ApiModelProperty(value = "当前阶段责任部门")
    private Integer cesrcdpt;

    @ApiModelProperty(value = "指定承办部门")
    private Integer cepcdpt0;

    @ApiModelProperty(value = "总时长")
    private Integer alldura;

    @ApiModelProperty(value = "处置流程")
    private String[] ceproc;

    @ApiModelProperty(value = "处置时长")
    private Integer pcdura;

    @ApiModelProperty(value = "关闭时间")
    private Date clsdt;

    @TableField(value = "ST_AsGeoJSON  ( st_transform(bexy, 4326 ) ) :: JSON")
    @ApiModelProperty(value = "经纬度")
    private Gis bexy;

    @ApiModelProperty(value = "事项来源代码")
    private Integer efbh;

    @ApiModelProperty(value = "协查任务描述")
    private String cpdesc;

    @ApiModelProperty(value = "协查反馈内容")
    private String cpfbinfo;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public static final String CEID = "ceid";

    public static final String PROC_INST_ID = "proc_inst_id";

    public static final String WFID = "wfid";

    public static final String CPBEDID = "cpbedid";

    public static final String CEBH = "cebh";

    public static final String CENM = "cenm";

    public static final String CECNT = "cecnt";

    public static final String ETBH = "etbh";

    public static final String HAPNDT = "hapndt";

    public static final String ADDR = "addr";

    public static final String ADID = "adid";

    public static final String TOWN_ADID = "town_adid";

    public static final String CEPCDPT = "cepcdpt";

    public static final String CESRCDPT = "cesrcdpt";

    public static final String CEPCDPT0 = "cepcdpt0";

    public static final String CEPROC = "ceproc";

    public static final String ALLDURA = "alldura";

    public static final String PCDURA = "pcdura";

    public static final String CLSDT = "clsdt";

    public static final String BEXY = "bexy";

    public static final String EFBH = "efbh";

    public static final String CPDESC = "cpdesc";

    public static final String CPFBINFO = "cpfbinfo";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String CESTAT = "cestat";

    @Override
    public String toString() {
        return "Coopevent{" +
        "ceid=" + ceid +
        ", procInstId=" + procInstId +
        ", wfid=" + wfid +
        ", cpbedid=" + cpbedid +
        ", cebh=" + cebh +
        ", cenm=" + cenm +
        ", cecnt=" + cecnt +
        ", etbh=" + etbh +
        ", hapndt=" + hapndt +
        ", addr=" + addr +
        ", adid=" + adid +
        ", townAdid=" + townAdid +
        ", cepcdpt=" + cepcdpt +
        ", cesrcdpt=" + cesrcdpt +
        ", cepcdpt0=" + cepcdpt0 +
        ", alldura=" + alldura +
        ", pcdura=" + pcdura +
        ", clsdt=" + clsdt +
        ", bexy=" + bexy +
        ", efbh=" + efbh +
        ", cpdesc=" + cpdesc +
        ", cpfbinfo=" + cpfbinfo +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
