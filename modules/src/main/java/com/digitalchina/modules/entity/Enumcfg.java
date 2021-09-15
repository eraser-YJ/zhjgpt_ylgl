package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 枚举配置
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Data
@TableName("public.enumcfg")
@ApiModel(value = "Enumcfg对象", description = "枚举配置")
public class Enumcfg implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "配置项编号(前端key值)")
	@TableId("kvid")
	private Integer kvid;

	@JsonIgnore
	@ApiModelProperty(value = "所属系统", hidden = true)
	private Integer appid;

	@ApiModelProperty(value = "枚举类型名称,即字典码")
	private String clsnm;

	@ApiModelProperty(value = "枚举类型说明")
	private String clsdescp;

	@JsonIgnore
	@ApiModelProperty(value = "某类型的int键", hidden = true)
	private Integer eikey;

	@JsonIgnore
	@ApiModelProperty(value = "某类型的text键", hidden = true)
	private String etkey;

	@ApiModelProperty(value = "某类型的值（数组）")
	private String[] values;

	@ApiModelProperty(value = "某类型的值(前端value值)")
	@TableField(exist = false)
	private String value;

	@JsonIgnore
	@ApiModelProperty(value = "扩展用", hidden = true)
	private String extend;

	@JsonIgnore
	@ApiModelProperty(value = "该键的说明", hidden = true)
	private String descp;

	@JsonIgnore
	@ApiModelProperty(value = "排序", hidden = true)
	private Integer ord;

	@JsonIgnore
	@ApiModelProperty(value = "创建时间", hidden = true)
	private Date crdt;

	@JsonIgnore
	@ApiModelProperty(value = "创建人", hidden = true)
	private Integer cruid;

	@JsonIgnore
	@ApiModelProperty(value = "修改时间", hidden = true)
	private Date modt;

	@JsonIgnore
	@ApiModelProperty(value = "修改人", hidden = true)
	private Integer mouid;

	@JsonIgnore
	@ApiModelProperty(value = "key锁定", hidden = true)
	private Boolean lockey;

	public static final String KVID = "kvid";

	public static final String APPID = "appid";

	public static final String CLSNM = "clsnm";

	public static final String CLSDESCP = "clsdescp";

	public static final String EIKEY = "eikey";

	public static final String ETKEY = "etkey";

	public static final String VALUES = "values";

	public static final String EXTEND = "extend";

	public static final String DESCP = "descp";

	public static final String ORD = "ord";

	public static final String CRDT = "crdt";

	public static final String CRUID = "cruid";

	public static final String MODT = "modt";

	public static final String MOUID = "mouid";

	public static final String LOCKEY = "lockey";

}
