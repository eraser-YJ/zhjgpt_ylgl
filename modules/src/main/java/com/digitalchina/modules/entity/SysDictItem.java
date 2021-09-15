package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Warrior
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dict_item")
@ApiModel(value = "SysDictItem对象", description = "")
public class SysDictItem implements Serializable {

	private static final long serialVersionUID = 1L;

	// 禁用
	public static final String STATUS_DISABLE = "0";
	// 启用
	public static final String STATUS_ENABLE = "1";

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "字典表ID")
	private String dictCode;

	@ApiModelProperty(value = "字典项名称")
	@NotNull(message = "字典项名称不能为空")
	private String itemName;

	@ApiModelProperty(value = "字典项值")
	@NotNull(message = "字典项值不能为空")
	private String itemValue;

	@ApiModelProperty(value = "排序")
	@NotNull(message = "排序不能为空")
	private Integer sort;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态：0：禁用  1：启用")
	private String status;

	@ApiModelProperty(value = "创建人")
	private String createBy;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新人")
	private String updateBy;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	public static final String ID = "id";

	public static final String DICT_CODE = "dict_code";

	public static final String ITEM_NAME = "item_name";

	public static final String ITEM_VALUE = "item_value";

	public static final String SORT = "sort";

	public static final String REMARK = "remark";

	public static final String STATUS = "status";

	public static final String CREATE_BY = "create_by";

	public static final String CREATE_TIME = "create_time";

	public static final String UPDATE_BY = "update_by";

	public static final String UPDATE_TIME = "update_time";

}
