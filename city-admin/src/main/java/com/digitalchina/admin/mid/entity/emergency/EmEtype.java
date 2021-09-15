package com.digitalchina.admin.mid.entity.emergency;

import cn.hutool.core.util.ObjectUtil;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 应急事件类型
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@TableName("emergency.em_etype")
@ApiModel(value="EmEtype对象", description="应急事件类型")
public class EmEtype implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("etypefk")
    @ApiModelProperty(value = "类型ID",required = false)
    private Integer etypefk;

    @ApiModelProperty(value = "类型名称",required = true)
    @NotNull(message = "类型名称不能为空")
    private String etypenm;

    @ApiModelProperty(value = "上级类型ID",required = true)
    @TableField(fill=FieldFill.UPDATE)
    private Integer etypetid;

    @ApiModelProperty(value = "上级类型ID清单",hidden = true)
    @TableField(fill=FieldFill.UPDATE)
    private Integer[] etypetids;
    
    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date crdt;

    @ApiModelProperty(value = "修改人",hidden = true)
    private Integer mouid;

    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date modt;

    public Integer getEtypefk() {
        return etypefk;
    }

    public void setEtypefk(Integer etypefk) {
        this.etypefk = etypefk;
    }
    public String getEtypenm() {
        return etypenm;
    }

    public void setEtypenm(String etypenm) {
        this.etypenm = etypenm;
    }
    public Integer getEtypetid() {
        return etypetid;
    }

    public void setEtypetid(Integer etypetid) {
        this.etypetid = etypetid;
    }
    public Integer[] getEtypetids() {
        return etypetids;
    }

    public void setEtypetids(Integer[] etypetids) {
        this.etypetids = etypetids;
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

    /**
     * 获取传入节点的当前路径
     * @param parent 父节点
     * @return
     */
    public  static Integer[] getPids(EmEtype parent){
        if(ObjectUtil.isEmpty(parent)){
            return null;
        }

        if(ObjectUtil.isEmpty(parent.getEtypetids())){
            Integer[] pids = new Integer[1] ;
            pids[0] = parent.getEtypefk();
            return pids;
        }
        Integer[] pids = Arrays.copyOf(parent.getEtypetids(),parent.getEtypetids().length+1);
        pids[parent.getEtypetids().length] = parent.getEtypefk();
        return pids;
    }

    public static final String ETYPEFK = "etypefk";

    public static final String ETYPENM = "etypenm";

    public static final String ETYPETID = "etypetid";

    public static final String ETYPETIDS = "etypetids";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MOUID = "mouid";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "EmEtype{" +
        "etypefk=" + etypefk +
        ", etypenm=" + etypenm +
        ", etypetid=" + etypetid +
        ", etypetids=" + etypetids +
        ", cruid=" + cruid +
        ", crdt=" + crdt +
        ", mouid=" + mouid +
        ", modt=" + modt +
        "}";
    }
}
