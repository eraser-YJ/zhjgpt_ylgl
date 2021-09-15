package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@TableName("admin.am_sys_user")
@ApiModel(value = "AmSysUser对象", description = "")
public class AmSysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "登录名")
    private String login;

    @JsonIgnore
    @ApiModelProperty(value = "经过加密的密码")
    @TableField(strategy = FieldStrategy.NOT_NULL)
    private String password;

    @ApiModelProperty(value = "昵称")
    private String name;

    @ApiModelProperty(value = "‎电话")
    private String mobile;

    @ApiModelProperty(value = "0：激活 1：冻结")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "修改日期")
    private Date modt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getModt() {
        return modt;
    }

    public void setModt(Date modt) {
        this.modt = modt;
    }

    public static final String ID = "id";

    public static final String LOGIN = "login";

    public static final String PASSWORD = "password";

    public static final String NAME = "name";

    public static final String MOBILE = "mobile";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";

    public static final String MODT = "modt";

    @Override
    public String toString() {
        return "AmSysUser{" + "id=" + id + ", login=" + login + ", password=" + password + ", name=" + name
                + ", mobile=" + mobile + ", status=" + status + ", remark=" + remark + ", modt=" + modt + "}";
    }
}
