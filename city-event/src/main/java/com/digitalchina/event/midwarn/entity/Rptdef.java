package com.digitalchina.event.midwarn.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 报告模板
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_rptdef")
@ApiModel(value = "Rptdef对象", description = "报告模板")
public class Rptdef implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报告模板ID")
    @TableId
    private Integer rdid;

    @ApiModelProperty(value = "模板所在路径")
    private String rdpath;

    @ApiModelProperty(value = "模板名称")
    private String rdnm;

    @ApiModelProperty(value = "时间名称表达式")
    private String rdnmexp;

    @ApiModelProperty(value = "时间范围表达式")
    private String rdrgexp;

    @ApiModelProperty(value = "禁用启用？")
    private Integer stat;

    @ApiModelProperty(value = "自动运行表达式")
    private String cron;

    @ApiModelProperty(value = "最后运行时间")
    private Date lastdt;

    @ApiModelProperty(value = "计划下一次运行的时间")
    private Date nextdt;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @ApiModelProperty(value = "组合属性值ID")
    private Integer cpvid;

    @ApiModelProperty(value = "报告周期（10日报，20周报，30月报，40季报，50半年报，60年报）")
    private Integer rpitv;

    @ApiModelProperty(value = "应用系统（0预警，1体征，2事件，3应急）")
    private Integer app;

    public static final String RDID = "rdid";

    public static final String RDPATH = "rdpath";

    public static final String RDNM = "rdnm";

    public static final String RDNMEXP = "rdnmexp";

    public static final String RDRGEXP = "rdrgexp";

    public static final String STAT = "stat";

    public static final String CRON = "cron";

    public static final String LASTDT = "lastdt";

    public static final String NEXTDT = "nextdt";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    public static final String CPVID = "cpvid";

    public static final String RPITV = "rpitv";

    public static final String APP = "app";

    @Override
    public String toString() {
        return "Rptdef{" +
                "rdid=" + rdid +
                ", rdpath=" + rdpath +
                ", rdnm=" + rdnm +
                ", rdnmexp=" + rdnmexp +
                ", rdrgexp=" + rdrgexp +
                ", stat=" + stat +
                ", cron=" + cron +
                ", lastdt=" + lastdt +
                ", nextdt=" + nextdt +
                ", crdt=" + crdt +
                ", modt=" + modt +
                ", cpvid=" + cpvid +
                ", rpitv=" + rpitv +
                ", app=" + app +
                "}";
    }
}
