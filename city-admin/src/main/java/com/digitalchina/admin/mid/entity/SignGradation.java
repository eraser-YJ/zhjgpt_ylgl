package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@TableName("sign.sign_gradation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SignGradation对象", description="")
public class SignGradation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "主键")
    private Integer gid;

    @ApiModelProperty(value = "方法名称")
    private String name;

    @ApiModelProperty(value = "处理类")
    private String clazz;

    @ApiModelProperty(value = "方法说明")
    private String meno;


    public static final String GID = "gid";

    public static final String NAME = "name";

    public static final String CLAZZ = "clazz";

    public static final String MENO = "meno";

}
