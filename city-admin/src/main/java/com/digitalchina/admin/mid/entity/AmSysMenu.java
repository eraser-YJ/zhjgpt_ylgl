package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Data
@TableName("admin.am_sys_menu")
@ApiModel(value = "AmSysMenu对象", description = "")
public class AmSysMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "父节点")
    private Integer pid;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单路径")
    private String url;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "序号")
    private Integer sort;

    @ApiModelProperty(value = "0:激活 1：禁用")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否打勾")
    private Integer state;

    public static final String ID = "id";

    public static final String PID = "pid";

    public static final String NAME = "name";

    public static final String URL = "url";

    public static final String ICON = "icon";

    public static final String SORT = "sort";

    public static final String STATUS = "status";

    public static final String REMARK = "remark";
}
