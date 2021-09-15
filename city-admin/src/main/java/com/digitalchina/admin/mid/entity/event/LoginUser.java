package com.digitalchina.admin.mid.entity.event;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.digitalchina.modules.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 登陆过事件系统的用户  admin里需要同步修改，被远程调用了
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@ApiModel(value="LoginUser对象", description="登陆过事件系统的用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(type = IdType.INPUT)
    private Integer id;

    @ApiModelProperty(value = "登陆名")
    private String login;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "所属部门")
    @TableField(exist = false)
    private String dept;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final String ID = "id";

    public static final String LOGIN = "login";

    public static final String NAME = "name";

    @Override
    public String toString() {
        return "LoginUser{" +
        "id=" + id +
        ", login=" + login +
        ", name=" + name +
        "}";
    }

    public LoginUser(SysUser sysUser){
        if (sysUser == null) {
            return;
        }
        BeanUtil.copyProperties(sysUser, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginUser loginUser = (LoginUser) o;
        return Objects.equals(id, loginUser.id) &&
                Objects.equals(login, loginUser.login) &&
                Objects.equals(name, loginUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name);
    }
}
