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
 * 事项总结
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.besum")
@ApiModel(value="Besum对象", description="事项总结")
public class Besum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "事项总结ID")
    @TableId("esumid")
    private Integer esumid;

    @ApiModelProperty(value = "事件ID")
    private Integer beid;

    @ApiModelProperty(value = "总结人")
    private Integer summan;

    @ApiModelProperty(value = "总结内容")
    private String sumcnt;

    @ApiModelProperty(value = "版本")
    private Integer rev;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public static final String ESUMID = "esumid";

    public static final String BEID = "beid";

    public static final String SUMMAN = "summan";

    public static final String SUMCNT = "sumcnt";

    public static final String REV = "rev";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Besum{" +
        "esumid=" + esumid +
        ", beid=" + beid +
        ", summan=" + summan +
        ", sumcnt=" + sumcnt +
        ", rev=" + rev +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
