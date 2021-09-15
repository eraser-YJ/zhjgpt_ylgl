package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 报告项
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_rptitm")
@ApiModel(value = "Rptitm对象", description = "报告项")
public class Rptitm implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "报告项ID")
    private Integer rtid;

    @ApiModelProperty(value = "报告模板ID")
    private Integer rdid;

    @ApiModelProperty(value = "项名称")
    private String rtnm;

    @ApiModelProperty(value = "项规则内容")
    private String rtcnt;

    @ApiModelProperty(value = "来源表名")
    private String itbnm;

    @ApiModelProperty(value = "来源SQL")
    private String isql;

    @ApiModelProperty(value = "输出格式")
    private Integer ofmt;

    @ApiModelProperty(value = "禁用启用？")
    private Integer stat;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public static final String RTID = "rtid";

    public static final String RDID = "rdid";

    public static final String RTNM = "rtnm";

    public static final String RTCNT = "rtcnt";

    public static final String ITBNM = "itbnm";

    public static final String ISQL = "isql";

    public static final String OFMT = "ofmt";

    public static final String STAT = "stat";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Rptitm{" +
                "rtid=" + rtid +
                ", rdid=" + rdid +
                ", rtnm=" + rtnm +
                ", rtcnt=" + rtcnt +
                ", itbnm=" + itbnm +
                ", isql=" + isql +
                ", ofmt=" + ofmt +
                ", stat=" + stat +
                ", crdt=" + crdt +
                ", modt=" + modt +
                "}";
    }
}
