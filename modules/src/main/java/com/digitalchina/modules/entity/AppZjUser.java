package com.digitalchina.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 住建部用户登录app时的实体类
 */
@Data
@TableName("user")
@ApiModel(value = "zjbUser对象", description = "")
public class AppZjUser {
    @ApiModelProperty(value = "自增ID")
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String userName;

    @ApiModelProperty(value = "用户唯一id")
    private String userGuid;

    @ApiModelProperty(value = "用户密码")
    private String password;


    @ApiModelProperty(value = "密码的盐值")
    private String salt;

    @ApiModelProperty(value = "真是姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String phone;


    @ApiModelProperty(value = "所属部门")
    private String ownDepart;


    @ApiModelProperty(value = "状态")
    private Integer state;


    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;


    public static final String ID = "id";

    public static final String USERGUID = "user_guid";

    public static final String USERNAME = "user_name";

    public static final String PASSWORD = "password";

}
