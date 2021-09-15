package com.digitalchina.zhjg.szss.mid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.zhjg.szss.constant.enums.GisType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 市政设施分类
 */
@TableName("szss.parts_category")
@Data
@ApiModel("市政部件分类")
public class PartsCategory implements Serializable {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("层级id路径")
    @JsonIgnore
    private Integer[] idPath; // 层级id路径
    @ApiModelProperty("父ID")
    private Integer parentId;
    @ApiModelProperty("所在层级")
    private Integer level; //当前分类层级
    @ApiModelProperty("分类代码")
    private String code; // 分类代码
    @ApiModelProperty("分类名称")
    private String name; // 分类名称
    @ApiModelProperty("同级排序")
    private Integer sort; // 同级排序
    @ApiModelProperty("说明")
    private String remark; // 说明
    @ApiModelProperty("图标名称")
    private String icon; // 图标名称
    @ApiModelProperty("图层GIS类型，点线面")
    private GisType gisType;
    @ApiModelProperty("分类数据所属模块")
    private String module;
    @ApiModelProperty("状态")
    private Boolean status;

    @TableField(exist = false)
    @JsonIgnore
    private PartsCategory parent;
    @TableField(exist = false)
    private List<PartsCategory> children; // 直接子分类
}
