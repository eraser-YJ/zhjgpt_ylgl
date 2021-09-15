package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.Date;

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
@TableName("sys_dict")
@ApiModel(value = "SysDict对象", description = "")
public class SysDict implements Serializable {

	private static final long serialVersionUID = 1L;

	// 禁用
	public static final String STATUS_DISABLE = "0";
	// 启用
	public static final String STATUS_ENABLE = "1";

	@ApiModelProperty(value = "主键")
	private Integer id;

	@ApiModelProperty(value = "字典名称")
	private String dictName;

	@ApiModelProperty(value = "字典编码")
	private String dictCode;

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

	public static final String DICT_NAME = "dict_name";

	public static final String DICT_CODE = "dict_code";

	public static final String REMARK = "remark";

	public static final String STATUS = "status";

	public static final String CREATE_BY = "create_by";

	public static final String CREATE_TIME = "create_time";

	public static final String UPDATE_BY = "update_by";

	public static final String UPDATE_TIME = "update_time";

}
