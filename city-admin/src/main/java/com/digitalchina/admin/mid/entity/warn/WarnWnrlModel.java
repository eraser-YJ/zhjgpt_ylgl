package com.digitalchina.admin.mid.entity.warn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 预警规则模型
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
@Data
@TableName("warn.warn_wnrl_model")
@ApiModel(value="WarnWnrlModel对象", description="预警规则模型")
public class WarnWnrlModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模型id")
    private Integer id;

    @ApiModelProperty(value = "模型名称")
    @NotNull(message = "模型名称不能为空")
    private String name;

    @ApiModelProperty(value = "模型描述")
    @NotNull(message = "模型描述不能为空")
    private String dsc;

    @ApiModelProperty(value = "参考文件")
    private String file;

    @ApiModelProperty(value = "消息模板")
    @NotNull(message = "消息模板不能为空")
    private String message;

    @ApiModelProperty(value = "1 启用、0 停用")
    private Integer status;

    @ApiModelProperty(value = "类型（0阀值，1趋势，2关联关系）")
    @NotNull(message = "模型类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "触发方式（0满足全部条件，1满足任意条件）")
    @NotNull(message = "触发方式不能为空")
    private Integer effect;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "更新时间")
    private Date modt;

    @Valid
    @TableField(exist = false)
    @ApiModelProperty(value = "参数列表")
    private List<WarnWnrlModelParam> params;

    @Valid
    @TableField(exist = false)
    @ApiModelProperty(value = "条件列表")
    private List<WarnWrnlModelCondition> conditions;

    @TableField(exist = false)
    @ApiModelProperty(value = "指标名称,以分号分隔,拓展字段输出用")
    private String quotas;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String DSC = "dsc";

    public static final String FILE = "file";

    public static final String MESSAGE = "message";

    public static final String STATUS = "status";

    public static final String TYPE = "type";

    public static final String EFFECT = "effect";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

}
