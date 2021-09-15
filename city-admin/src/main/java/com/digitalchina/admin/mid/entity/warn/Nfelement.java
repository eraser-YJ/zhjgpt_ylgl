package com.digitalchina.admin.mid.entity.warn;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 业务组件
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-25
 */
@Data
@TableName("warn.warn_nfelement")
@ApiModel(value = "WarnNfelement对象", description = "业务组件")
public class Nfelement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ApiModelProperty(value = "组件名称")
	private String name;

	@ApiModelProperty(value = "数据源（表")
	private String source;

	@ApiModelProperty(value = "指标Code")
	private String[] mscode;

	@ApiModelProperty(value = "专题主题")
	private String[] cpvs;

	@ApiModelProperty(value = "类型（静态、动态")
	private String type;

	@ApiModelProperty(value = "委办局Id")
	private Integer cmnid;

	@ApiModelProperty(value = "频率")
	private String frequency;

	private String server;

	@ApiModelProperty(value = "默认形状")
	private String defshape;

	@ApiModelProperty(value = "其他形状")
	private String othershape;

	public static final String ID = "id";

	public static final String NAME = "name";

	public static final String SOURCE = "source";

	public static final String MSCODE = "mscode";

	public static final String CPVS = "cpvs";

	public static final String TYPE = "type";

	public static final String CMNID = "cmnid";

	public static final String FREQUENCY = "frequency";

	public static final String SERVER = "server";

	public static final String DEFSHAPE = "defshape";

	public static final String OTHERSHAPE = "othershape";

	public static final String SPECIAL = "cpvs[1]";

	public static final String TOPIC = "cpvs[2]";
}
