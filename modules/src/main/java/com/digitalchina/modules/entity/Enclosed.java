package com.digitalchina.modules.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 应急预案管理_附件表
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
@Data
@TableName("public.enclosed")
@ApiModel(value="Enclosed对象", description="应急预案管理_附件表")
public class Enclosed implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @TableId("ecid")
    @ApiModelProperty(value = "附件编号")
    private Integer ecid;

    @ApiModelProperty(value = "所属系统",hidden = true)
    private Integer appid;

    @ApiModelProperty(value = "主表类型（应用内部自行协调)",hidden = true)
    private Integer msttype;

    @ApiModelProperty(value = "主表的记录编号",hidden = true)
    private Integer mstid;

    @ApiModelProperty(value = "附件名称",required = true)
    private String ecnm;

    @ApiModelProperty(value = "附件Key",required = true)
    private String eckey;

    @ApiModelProperty(value = "附件类型（1:img/png,2:vedio,3:pdf,4:word）",required = true)
    private Integer ectype;

    @ApiModelProperty(value = "排序",hidden = true)
    private Integer ord;

    @ApiModelProperty(value = "创建人",hidden = true)
    private Integer cruid;

    @ApiModelProperty(value = "创建时间",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime crdt;

    public static final String ECID = "ecid";

    public static final String APPID = "appid";

    public static final String MSTTYPE = "msttype";

    public static final String MSTID = "mstid";

    public static final String ECNM = "ecnm";

    public static final String ECKEY = "eckey";

    public static final String ECTYPE = "ectype";

    public static final String ORD = "ord";

    public static final String CRUID = "cruid";

    public static final String CRDT = "crdt";

}
