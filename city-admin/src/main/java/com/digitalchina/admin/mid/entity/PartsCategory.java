package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.constant.enums.GisType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 市政设施分类
 */
@TableName("szss.parts_category")
@Data
@ApiModel("市政部件分类")
public class PartsCategory implements Serializable {

    @TableId
    private Integer id;
    private Integer parentId;
    private String code; // 分类代码
    private String name; // 分类名称
    private Integer level; // 层级

    private GisType gisType;

}
