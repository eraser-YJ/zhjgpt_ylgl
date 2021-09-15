package com.digitalchina.admin.mid.entity.emergency;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 应急部门
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
@TableName("emergency.em_emdept")
@ApiModel(value="EmEmdept对象", description="应急部门")
public class EmEmdept implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("dpid")
    @ApiModelProperty(value = "部门ID",required = false)
    private Integer dpid;

    @ApiModelProperty(value = "部门名称",required = true)
    private String bdnm;

    @ApiModelProperty(value = "上级部门ID",required = true)
    private Integer bdpntid;

    @ApiModelProperty(value = "上级部门ID清单",hidden = true)
    private Integer[] bdpntids;

    @ApiModelProperty(value = "部门类型(备用)",hidden = true)
    private Integer bdtype;

    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date crdt;

    @ApiModelProperty(value = "修改人",hidden = true)
    private Integer mouid;

    @ApiModelProperty(value = "修改时间",hidden = true)
    private Date modt;

    public Integer getDpid() {
        return dpid;
    }

    public void setDpid(Integer dpid) {
        this.dpid = dpid;
    }
    public String getBdnm() {
        return bdnm;
    }

    public void setBdnm(String bdnm) {
        this.bdnm = bdnm;
    }
    public Integer getBdpntid() {
        return bdpntid;
    }

    public void setBdpntid(Integer bdpntid) {
        this.bdpntid = bdpntid;
    }
    public Integer[] getBdpntids() {
        return bdpntids;
    }

    public void setBdpntids(Integer[] bdpntids) {
        this.bdpntids = bdpntids;
    }
    public Integer getBdtype() {
        return bdtype;
    }

    public void setBdtype(Integer bdtype) {
        this.bdtype = bdtype;
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
    public  static Integer[] getPids(EmEmdept parent){
        if(ObjectUtil.isEmpty(parent)){
            return null;
        }

        if(ObjectUtil.isEmpty(parent.getBdpntids())){
            Integer[] pids = new Integer[1] ;
            pids[0] = parent.getDpid();
            return pids;
        }
        Integer[] pids = Arrays.copyOf(parent.getBdpntids(),parent.getBdpntids().length+1);
        pids[parent.getBdpntids().length] = parent.getDpid();
        return pids;
    }

    public static final String DPID = "dpid";

    public static final String BDNM = "bdnm";

    public static final String BDPNTID = "bdpntid";

    public static final String BDPNTIDS = "bdpntids";

    public static final String BDTYPE = "bdtype";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

    public static final String MOUID = "mouid";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "EmEmdept{" +
        "dpid=" + dpid +
        ", bdnm=" + bdnm +
        ", bdpntid=" + bdpntid +
        ", bdpntids=" + bdpntids +
        ", bdtype=" + bdtype +
        ", cruid=" + cruid +
        ", crdt=" + crdt +
        ", mouid=" + mouid +
        ", modt=" + modt +
        "}";
    }
}
