package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 任务日志
 * </p>
 *
 * @author Warrior
 * @since 2019-09-04
 */
@TableName("warn.warn_tsklog")
@ApiModel(value="Tsklog对象", description="任务日志")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tsklog implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    @ApiModelProperty(value = "日志ID")
    private Integer logid;

    @ApiModelProperty(value = "0预警，1报告")
    private Integer tsktp;

    @ApiModelProperty(value = "外键")
    private Integer fr;

    @ApiModelProperty(value = "外部数据的时间戳")
    private Date frmodt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Date endt;

    @ApiModelProperty(value = "错误日志")
    private String err;

    @ApiModelProperty(value = "警告日志")
    private String warn;

    @ApiModelProperty(value = "正常日志")
    private String normal;

    @ApiModelProperty(value = "-1：未结束 0：成功 1：失败")
    private Integer status;

    @ApiModelProperty(value = "自动任务？")
    private Boolean autoab;
    
    /**
     * 状态 @see StateEnum
     */
    private Integer state;

    public static final String LOGID = "logid";

    public static final String TSKTP = "tsktp";

    public static final String FR = "fr";

    public static final String FRMODT = "frmodt";

    public static final String CRDT = "crdt";

    public static final String ENDT = "endt";

    public static final String ERR = "err";

    public static final String WARN = "warn";

    public static final String NORMAL = "normal";

    public static final String STATUS = "status";

    public static final String AUTOAB = "autoab";

}
