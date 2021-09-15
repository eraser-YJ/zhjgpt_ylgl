package com.digitalchina.event.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 * 部门-角色关系表
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@ApiModel(value="DeptUser对象", description="部门-角色关系表")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId
    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer uid;

    @ApiModelProperty(value = "部门id")
    private Integer did;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public static final String ID = "id";

    public static final String UID = "uid";

    public static final String DID = "did";

    @Override
    public String toString() {
        return "DeptUser{" +
        "id=" + id +
        ", uid=" + uid +
        ", did=" + did +
        "}";
    }
}
