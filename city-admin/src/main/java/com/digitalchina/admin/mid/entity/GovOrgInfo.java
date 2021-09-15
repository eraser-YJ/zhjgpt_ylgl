package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 政府组织信息表
 * </p>
 *
 * @author Warrior
 * @since 2019-09-05
 */
@TableName("admin.am_gov_org_info")
@ApiModel(value="GovOrgInfo对象", description="政府组织信息表")
@Data
public class GovOrgInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "编号", required = true)
    @NotNull(message = "编号不能为空")
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    @NotNull(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "所属委办局id", required = true)
    @NotNull(message = "所属委办局id不能为空")
    private Integer cmnid;

    @ApiModelProperty(value = "对接人")
    private String docker;

    @ApiModelProperty(value = "对接人联系方式")
    private String contact;

    @ApiModelProperty(value = "创建时间")
    private Date crdt;

    @ApiModelProperty(value = "修改时间")
    private Date modt;

    @ApiModelProperty(value = "委办局名称",hidden = true)
    @TableField(exist = false)
    private String cmnnm;

    @ApiModelProperty(value = "委办局原名称",hidden = true)
    @TableField(exist = false)
    private String cmnnm2;

    public static final String ID = "id";

    public static final String CODE = "code";

    public static final String NAME = "name";

    public static final String CMNID = "cmnid";

    public static final String DOCKER = "docker";

    public static final String CONTACT = "contact";

    public static final String CRDT = "crdt";

    public static final String MODT = "modt";

}
