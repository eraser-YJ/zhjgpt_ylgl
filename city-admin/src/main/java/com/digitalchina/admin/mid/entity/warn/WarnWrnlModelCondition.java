package com.digitalchina.admin.mid.entity.warn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
@Data
@TableName("warn.warn_wrnl_model_condition")
@ApiModel(value="WarnWrnlModelCondition对象", description="")
public class WarnWrnlModelCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "条件ID")
    private Integer id;

    @ApiModelProperty(value = "参数ID")
    private Integer paramid;

    @TableField(exist = false)
    @ApiModelProperty(value = "参数code")
    @NotNull(message ="参数code不能为空")
    private String code1;

    @ApiModelProperty(value = "范围（最新newest，最大max，最小min，平均avg，总和sum）")
    private String range;

    @ApiModelProperty(value = "运算方式（大于gt，大于等于gte，小于lt，小于等于lte，不等于nt，持续上升kr，持续下降kd，包含cont）")
    @NotNull(message ="运算方式不能为空")
    private String operator;

    @ApiModelProperty(value = "多个用逗号隔开")
    private String val;

    @ApiModelProperty(value = "描述")
    private String dsc;

    @ApiModelProperty(value = "模型id")
    private Integer modelid;

    @ApiModelProperty(value = "另一个参数id")
    private Integer field;

    @TableField(exist = false)
    @ApiModelProperty(value = "另一个参数code")
    private String code2;


    public static final String ID = "id";

    public static final String PARAMID = "paramid";

    public static final String RANGE = "range";

    public static final String OPERATOR = "operator";

    public static final String VAL = "val";

    public static final String DSC = "dsc";

    public static final String MODELID = "modelid";

    public static final String FIELD = "field";

}
