package com.digitalchina.admin.mid.entity.emergency;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.modules.entity.Enclosed;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应急预案
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
@Data
@TableName("emergency.em_plan")
@ApiModel(value="EmPlan对象", description="应急预案")
public class EmPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("planid")
    @ApiModelProperty(value = "预案编号")
    private Integer planid;

    @ApiModelProperty(value = "预案名称",required = true)
    private String pname;

    @ApiModelProperty(value = "预案类型（预警里组合属性值）",required = true)
    private Integer ptypefk;

    @ApiModelProperty(value = "事件类型（预警里组合属性值）",required = true)
    private Integer etypefk;

    @ApiModelProperty(value = "事件等级",required = true)
    private Integer elevelfk;

    @ApiModelProperty(value = "应急事件集合",required = false)
    private Integer[] evids;

    @ApiModelProperty(value = "预案文件描述",required = true)
    private String pdesc;
    
    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;
    
    @ApiModelProperty(value = "创建时间")
    private Date crdt;
    
    @TableField(exist = false)
    @ApiModelProperty(value = "应急预案管理_附件表")
    private Enclosed enclosed;

    public static final String PLANID = "planid";

    public static final String PNAME = "pname";

    public static final String PTYPEFK = "ptypefk";

    public static final String ETYPEFK = "etypefk";

    public static final String ELEVELFK = "elevelfk";

    public static final String EVIDS = "evids";

    public static final String PDESC = "pdesc";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";
    
}
