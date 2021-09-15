package com.digitalchina.event.mid.entity;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;

/**
 * <p>
 * 事件部门
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.bedept")
@ApiModel(value="Bedept对象", description="事件部门")
public class Bedept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门ID")
    @TableId
    private Integer bedid;

    @ApiModelProperty(value = "部门名称", required = true)
    @NotNull(message = "部门名称不能为空")
    private String bdnm;

    @ApiModelProperty(value = "上级部门ID",required = true)
    @TableField(fill=FieldFill.UPDATE)
    private Integer bdpntid;

    @ApiModelProperty(value = "上级部门ID清单")
    @TableField(fill=FieldFill.UPDATE)
    private Integer[] bdpntids;

    @ApiModelProperty(value = "部门类型", required = true)
    @NotNull(message = "部门类型不能为空")
    private Integer bdtype;

    @ApiModelProperty(value = "父级部门名称",hidden = true)
    @TableField(exist = false)
    private String pnm;

    public static final String BEDID = "bedid";

    public static final String BDNM = "bdnm";

    public static final String BDPNTID = "bdpntid";

    public static final String BDPNTIDS = "bdpntids";

    public static final String BDTYPE = "bdtype";

    @Override
    public String toString() {
        return "Bedept{" +
        "bedid=" + bedid +
        ", bdnm=" + bdnm +
        ", bdpntid=" + bdpntid +
        ", bdpntids=" + bdpntids +
        ", bdtype=" + bdtype +
        "}";
    }

    /**
     * 获取传入节点的当前路径
     * @param parent 父节点
     * @return
     */
	public Integer[] getPids(){
        if(ObjectUtil.isEmpty(this)){
            return null;
        }

        if(ObjectUtil.isEmpty(this.getBdpntids())){
            Integer[] pids = new Integer[1] ;
            pids[0] = this.getBedid();
            return pids;
        }
        Integer[] pids = Arrays.copyOf(this.getBdpntids(),this.getBdpntids().length+1);
        pids[this.getBdpntids().length] = this.getBedid();
        return pids;
    }

}
