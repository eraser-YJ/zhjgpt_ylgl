package com.digitalchina.admin.mid.entity.warn;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 预警规则模型参数
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
@Data
@TableName("warn.warn_wnrl_model_param")
@ApiModel(value="WarnWnrlModelParam对象", description="预警规则模型参数")
public class WarnWnrlModelParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数id")
    private Integer id;

    @ApiModelProperty(value = "参数名")
    @NotNull(message = "参数名不能为空")
    private String name;

    @ApiModelProperty(value = "参数Code")
    @NotNull(message = "参数Code不能为空")
    private String code;

    @ApiModelProperty(value = "类型（Float,String,Array)")
    @NotNull(message = "类型不能为空")
    private String type;

    @ApiModelProperty(value = "说明")
    private String dsc;

    @ApiModelProperty(value = "模型Id")
    private Integer modelid;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String CODE = "code";

    public static final String TYPE = "type";

    public static final String DSC = "dsc";

    public static final String MODELID = "modelid";

}
