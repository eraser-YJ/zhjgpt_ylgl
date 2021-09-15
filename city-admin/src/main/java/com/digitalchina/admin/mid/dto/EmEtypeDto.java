package com.digitalchina.admin.mid.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应急事件类型Dto
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@Data
@ApiModel("应急事件类型Dto")
public class EmEtypeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型ID")
    private Integer etypefk;

    @ApiModelProperty(value = "类型名称")
    private String etypenm;
    
    @ApiModelProperty(value = "上级类型ID")
    private Integer etypetid;
    
    @ApiModelProperty(value = "上级类型ID清单")
    private Integer[] etypetids;
    
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
    
    private String exname;

}