package com.digitalchina.admin.mid.entity.emergency;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 应急事件等级
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@TableName("emergency.em_erank")
@ApiModel(value="EmErank对象", description="应急事件等级")
public class EmErank implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("elevelfk")
    @ApiModelProperty(value = "等级ID",hidden = true)
    private Integer elevelfk;

    @ApiModelProperty(value = "等级名称")
    private String elevelnm;

    @ApiModelProperty(value = "颜色标识")
    private String color;
    
    @JsonIgnore
    @ApiModelProperty(value = "排序",hidden = true)
    private Integer sort;
    
    @JsonIgnore
    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;
    
    @JsonIgnore
    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date crdt;
    
    @JsonIgnore
    @ApiModelProperty(value = "修改人",hidden = true)
    private Integer mouid;
    
    @JsonIgnore
    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date modt;

    public Integer getElevelfk() {
        return elevelfk;
    }

    public void setElevelfk(Integer elevelfk) {
        this.elevelfk = elevelfk;
    }
    public String getElevelnm() {
        return elevelnm;
    }

    public void setElevelnm(String elevelnm) {
        this.elevelnm = elevelnm;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    public Integer getCruid() {
        return cruid;
    }

    public void setCruid(Integer cruid) {
        this.cruid = cruid;
    }
    public Date getCrdt() {
        return crdt;
    }

    public void setCrdt(Date crdt) {
        this.crdt = crdt;
    }
    public Integer getMouid() {
        return mouid;
    }

    public void setMouid(Integer mouid) {
        this.mouid = mouid;
    }
    public Date getModt() {
        return modt;
    }

    public void setModt(Date modt) {
        this.modt = modt;
    }

    public static final String ELEVELFK = "elevelfk";

    public static final String ELEVELNM = "elevelnm";

    public static final String COLOR = "color";

    public static final String SORT = "sort";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MOUID = "mouid";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "EmErank{" +
        "elevelfk=" + elevelfk +
        ", elevelnm=" + elevelnm +
        ", color=" + color +
        ", sort=" + sort +
        ", cruid=" + cruid +
        ", crdt=" + crdt +
        ", mouid=" + mouid +
        ", modt=" + modt +
        "}";
    }
}
