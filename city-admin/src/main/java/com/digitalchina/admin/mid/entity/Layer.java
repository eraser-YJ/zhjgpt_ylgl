package com.digitalchina.admin.mid.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 图层
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_layer")
@ApiModel(value = "Layer对象", description = "图层")
public class Layer implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "图层ID")
	@TableId("lyid")
	private Integer lyid;

	@ApiModelProperty(value = "委办局ID")
	private Integer cmnid;

	@ApiModelProperty(value = "图层名称")
	private String lynm;

	@ApiModelProperty(value = "关联图层")
	private Integer[] asslys;

	@ApiModelProperty(value = "业务分类（1基础管理、2交通出行、3公共安全、4生态环境、5民生服务")
	private Integer srctp;

	@ApiModelProperty(value = "禁用启用（1禁用，0启用")
	private Integer stat;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	@ApiModelProperty(value = "描述")
	private String remark;

	@ApiModelProperty(value = "来源地址")
	private String source;

	@ApiModelProperty(value = "来源类型")
	private String sourcetype;

	@ApiModelProperty(value = "属性（1静态资源，2.动态资源")
	private Integer prop;

	@ApiModelProperty(value = "部门ID")
	private Integer dpid;

	@ApiModelProperty(value = "部门ID")
	private Integer[] dpids;

	@TableField(exist = false)
	@ApiModelProperty(value = "部门名称（只用于列表展示")
	private String dpnm;

	public static final String LYID = "lyid";

	public static final String CMNID = "cmnid";

	public static final String LYNM = "lynm";

	public static final String REMARK = "remark";

	public static final String ASSLYS = "asslys";

	public static final String SRCTP = "srctp";

	public static final String STAT = "stat";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	public static final String PROP = "prop";

	public static final String DPID = "dpid";

}