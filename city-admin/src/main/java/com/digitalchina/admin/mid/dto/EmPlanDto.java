package com.digitalchina.admin.mid.dto;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 应急预案Dto
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
@Data
@ApiModel(value="EmPlanDto对象", description="应急预案Dto")
public class EmPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("planid")
    @ApiModelProperty(value = "预案编号")
    private Integer planid;

    @ApiModelProperty(value = "预案名称",required = true)
    private String pname;

    @ApiModelProperty(value = "预案类型名称",required = true)
    private String ptypenm;
    
    @ApiModelProperty(value = "预案类型id",required = true)
    private Integer ptypefk;

    @ApiModelProperty(value = "事件类型（预警里组合属性值）",required = true)
    private String etypenm;
    
    @ApiModelProperty(value = "事件类型父级id",required = true)
    private Integer etypetid;
    
    @ApiModelProperty(value = "事件类型父级id集合",required = true)
    private Integer[] etypetids;

    @ApiModelProperty(value = "事件等级名称",required = true)
    private String elevelnm;
    
    @ApiModelProperty(value = "事件等级id",required = true)
    private Integer elevelfk;

    @TableField(exist = false)
    @ApiModelProperty(value = "应急预案管理_附件的key")
    private String eckey;
    
    @ApiModelProperty(value = "附件名称",required = true)
    private String ecnm;
    
    @ApiModelProperty(value = "预案文件描述",required = true)
    private String pdesc;
    
    @ApiModelProperty(value = "应急事件名称",required = false)
    private String evidsnm;
    
    @ApiModelProperty(value = "应急事件id集合",required = false)
    private Integer[] evids;

    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;
    
    @ApiModelProperty(value = "创建时间")
    private Date crdt;
    

    public static final String PLANID = "planid";

    public static final String PNAME = "pname";

    public static final String PTYPENM = "ptypenm";

    public static final String PTYPEFK = "ptypefk";
    
    public static final String ETYPENM = "etypenm";
    
    public static final String ETYPETID = "etypetid";
    
    public static final String ETYPETIDS = "etypetids";

    public static final String ELEVELNM = "elevelnm";
    
    public static final String ELEVELFK = "elevelfk";
    
    public static final String ECKEY = "eckey";
    
    public static final String ECNM = "ecnm";

    public static final String EVIDSNM = "evidsnm";
    
    public static final String EVIDS = "evids";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";
    
}
