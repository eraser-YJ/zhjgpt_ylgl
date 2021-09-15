package com.digitalchina.admin.mid.dto;

import cn.hutool.core.bean.BeanUtil;
import com.digitalchina.admin.mid.entity.SignTreeVer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@ApiModel(value="SignTreeVer对象", description="")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignTreeVerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "版本名称不能为空")
    @ApiModelProperty(value = "版本名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String meno;

    @ApiModelProperty(value = "所选模板id")
    private Integer pid;

    public SignTreeVer toSignTreeVer(){
        SignTreeVer signTreeVer = new SignTreeVer();
        BeanUtil.copyProperties(this,signTreeVer);
        return signTreeVer;
    }
}
