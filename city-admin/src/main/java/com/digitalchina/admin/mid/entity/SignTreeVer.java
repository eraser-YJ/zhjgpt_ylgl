package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@Builder
@TableName("sign.sign_tree_ver")
@ApiModel(value="SignTreeVer对象", description="")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignTreeVer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "主键")
    private Integer tid;

    @ApiModelProperty(value = "版本名称")
    @NotNull(message = "版本名称不能为空")
    private String name;

    @ApiModelProperty(value = "备注")
    private String meno;

    @ApiModelProperty(value = "状态(1：启用  0：不启动)")
    private Integer status;


    public static final String TID = "tid";

    public static final String NAME = "name";

    public static final String MENO = "meno";

    public static final String STATUS = "status";

}
