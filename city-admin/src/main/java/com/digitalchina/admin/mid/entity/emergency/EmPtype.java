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
 * 应急预案类型
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@TableName("emergency.em_ptype")
@ApiModel(value="EmPtype对象", description="应急预案类型")
public class EmPtype implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("ptypefk")
    @ApiModelProperty(value = "类型ID",hidden = true)
    private Integer ptypefk;

    @ApiModelProperty(value = "类型名称")
    private String ptypenm;

    @ApiModelProperty(value = "排序")
    private Integer sort;
    @JsonIgnore
    @ApiModelProperty(value = "创建人")
    private Integer cruid;
    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    private Date crdt;
    @JsonIgnore
    @ApiModelProperty(value = "修改人")
    private Integer mouid;
    @JsonIgnore
    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public Integer getPtypefk() {
        return ptypefk;
    }

    public void setPtypefk(Integer ptypefk) {
        this.ptypefk = ptypefk;
    }
    public String getPtypenm() {
        return ptypenm;
    }

    public void setPtypenm(String ptypenm) {
        this.ptypenm = ptypenm;
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

    public static final String PTYPEFK = "ptypefk";

    public static final String PTYPENM = "ptypenm";

    public static final String SORT = "sort";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MOUID = "mouid";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "EmPtype{" +
        "ptypefk=" + ptypefk +
        ", ptypenm=" + ptypenm +
        ", sort=" + sort +
        ", cruid=" + cruid +
        ", crdt=" + crdt +
        ", mouid=" + mouid +
        ", modt=" + modt +
        "}";
    }
}
