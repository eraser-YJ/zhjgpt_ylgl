package com.digitalchina.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("SDE.SZSS_USER")
@ApiModel(value = "szssUser对象", description = "")
public class AppSysUser implements Serializable {
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户年龄")
    private String userAge;

    @ApiModelProperty(value = "用户账号")
    private String userAccnum;

    @ApiModelProperty(value = "用户密码")
    private String userPassword;

    @ApiModelProperty(value = "用户性别")
    private String userSex;

    @ApiModelProperty(value = "用户关联公司id")
    private Integer enterId;

    private String status;

    @ApiModelProperty(value = "用户手机号")
    private String userPhone;

    @ApiModelProperty(value = "用户所属公司")
    private String enterName;


    public static final String ID = "userId";

    public static final String SSOID = "ssoid";

    public static final String USERACCNUM = "USER_ACCNUM";

    public static final String USERPASSWORD = "USER_PASSWORD";

    public static final String USERNAME = "USER_NAME";
}