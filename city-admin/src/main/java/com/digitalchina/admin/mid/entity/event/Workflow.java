package com.digitalchina.admin.mid.entity.event;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 工作流
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.workflow")
@ApiModel(value="Workflow对象", description="工作流")
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "流程ID")
    @TableId("wfid")
    private Integer wfid;

    @ApiModelProperty(value = "流程名称",required = true)
    @NotNull(message = "流程名称不能为空")
    private String wfnm;

    @ApiModelProperty(value = "流程Key",required = true)
    @NotNull(message = "流程Key不能为空")
    private String wfkey;

    @ApiModelProperty(value = "版本",required = true)
    @NotNull(message = "版本不能为空")
    private Integer rev;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    public static final String WFID = "wfid";

    public static final String WFNM = "wfnm";

    public static final String WFKEY = "wfkey";

    public static final String REV = "rev";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "Workflow{" +
        "wfid=" + wfid +
        ", wfnm=" + wfnm +
        ", wfkey=" + wfkey +
        ", rev=" + rev +
        ", crdt=" + crdt +
        ", modt=" + modt +
        "}";
    }
}
