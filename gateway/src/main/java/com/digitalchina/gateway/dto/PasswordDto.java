package com.digitalchina.gateway.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 密码对象Dto
 * </p>
 *
 * @author liuping
 * @since 2019-10-30
 */
@Data
@ApiModel(value = "密码对象Dto", description = "")
public class PasswordDto implements Serializable {

    private static final long serialVersionUID = -1707298008877357434L;

    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
