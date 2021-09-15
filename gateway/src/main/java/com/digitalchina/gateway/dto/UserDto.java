package com.digitalchina.gateway.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *用户对象Dto
 * </p>
 *
 * @author liuping
 * @since 2019-10-30
 */
@Data
@ApiModel(value = "用户对象Dto", description = "")
public class UserDto implements Serializable {

    private static final long serialVersionUID = 142091956714170929L;

    @ApiModelProperty(value = "系统标识：cityapp:领导决策APP")
    @NotBlank(message = "系统标识不能为空")
    private String app;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String login;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
