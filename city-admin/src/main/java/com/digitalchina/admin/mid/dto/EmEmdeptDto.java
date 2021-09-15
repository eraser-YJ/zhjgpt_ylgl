package com.digitalchina.admin.mid.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应急部门Dto
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
@Data
@ApiModel("应急部门Dto")
public class EmEmdeptDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门ID")
    private Integer dpid;

    @ApiModelProperty(value = "部门名称")
    private String bdnm;
    
    @ApiModelProperty(value = "上级部门ID")
    private Integer bdpntid;
    
    @ApiModelProperty(value = "上级部门ID清单")
    private Integer[] bdpntids;
    
    @ApiModelProperty(value = "节点连级名称")
    private String exname;
    
    @ApiModelProperty(value = "部门类型(备用)")
    private Integer bdtype;
    
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

}
