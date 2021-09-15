package com.digitalchina.admin.mid.entity.emergency;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.mid.entity.Gis;
import com.digitalchina.admin.utils.PgGisTypeHandler;
import com.digitalchina.modules.entity.Enclosed;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应急事件
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Data
@TableName("emergency.em_event")
@ApiModel(value="EmEvent对象", description="应急事件")
public class EmEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("evid")
    @ApiModelProperty(value = "应急事件编号")
    private Integer evid;

    @ApiModelProperty(value = "预案编号")
    private Integer planid;

    @ApiModelProperty(value = "预应急事件编号",notes = "从事件列表里选择的事件，需要输入这个")
    private Integer previd;

    @ApiModelProperty(value = "事件名称")
    @NotNull(message = "事件名称不能为空")
    private String ename;

    @ApiModelProperty(value = "事件编号（自动分配）")
    private String eno;

    @ApiModelProperty(value = "事件类型（预警里组合属性值）")
    @NotNull(message = "事件类型不能为空")
    private Integer etypefk;

    @ApiModelProperty(value = "事件等级")
    @NotNull(message = "事件等级不能为空")
    private Integer elevelfk;

    @ApiModelProperty(value = "事发地点")
    @NotNull(message = "事发地点不能为空")
    private String hpnaddr;

    @ApiModelProperty(value = "事发区域（公共配置表nf）")
    @NotNull(message = "事发区域不能为空")
    private Integer hpnarea;

    /**
     * 坐标对象,需要转换成数据库坐标对象和大地坐标系, 隐式在typeHandler和sqlHandler中执行 不需要关注这块逻辑
     * ps:不同地图采用坐标系不同,需要对应
     *
     * @see PgGisTypeHandler
     */
    @Valid
    @TableField(value = "ST_AsGeoJSON  ( st_transform(xy, 4326 ) ) :: JSON")
    @ApiModelProperty(value = "经纬度",notes = "输出用,输入不要用这个")
    private Gis xy;

    @ApiModelProperty(value = "事件来源")
    @NotNull(message = "事件来源不能为空")
    private Integer eevsrc;

    @ApiModelProperty(value = "影响范围")
    @NotNull(message = "影响范围不能为空")
    private Integer eftrngfk;

    @ApiModelProperty(value = "事件描述")
    @NotNull(message = "事件描述不能为空")
    private String evtdesc;

    @ApiModelProperty(value = "处置过程总结")
    private String eprocsum;

    @ApiModelProperty(value = "处置经验总结")
    private String expncsum;

    @ApiModelProperty(value = "进入案例库")
    private Boolean inrepo;

    @ApiModelProperty(value = "处置总结及善后计划")
    private String aftdlplan;

    @ApiModelProperty(value = "1：处理中2：善后中3：结束")
    private Integer evtst;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "事发时间")
    @NotNull(message = "事发时间不能为空")
    private Date hpdt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上报时间")
    @NotNull(message = "上报时间不能为空")
    private Date rptdt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "总结时间")
    private Date sumydt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "善后开始时间")
    private Date aftdldt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "事件结束时间")
    private Date enddt;


    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date crdt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改人",hidden = true)
    private Integer mouid;

    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date modt;


    @Valid
    @TableField(exist = false)
    @ApiModelProperty(value = "关联附件", notes = "附件最多5个，前端控制")
    private List<Enclosed> encloseds;


    public static final String EVID = "evid";

    public static final String PLANID = "planid";

    public static final String PREVID = "previd";

    public static final String ENAME = "ename";

    public static final String ENO = "eno";

    public static final String ETYPEFK = "etypefk";

    public static final String ELEVELFK = "elevelfk";

    public static final String HPNADDR = "hpnaddr";

    public static final String HPNAREA = "hpnarea";

    public static final String XY = "xy";

    public static final String EEVSRC = "eevsrc";

    public static final String EFTRNGFK = "eftrngfk";

    public static final String EVTDESC = "evtdesc";

    public static final String EPROCSUM = "eprocsum";

    public static final String EXPNCSUM = "expncsum";

    public static final String INREPO = "inrepo";

    public static final String AFTDLPLAN = "aftdlplan";

    public static final String EVTST = "evtst";

    public static final String HPDT = "hpdt";

    public static final String RPTDT = "rptdt";

    public static final String SUMYDT = "sumydt";

    public static final String AFTDLDT = "aftdldt";

    public static final String ENDDT = "enddt";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MOUID = "mouid";

    public static final String MODT = "modt";

}
