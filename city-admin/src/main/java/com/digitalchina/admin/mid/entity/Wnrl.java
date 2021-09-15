package com.digitalchina.admin.mid.entity;

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
 * 警告规则
 * </p>
 *
 * @author Warrior
 * @since 2019-09-02
 */
@TableName("warn.warn_wnrl")
@ApiModel(value = "Wnrl对象", description = "警告规则")
@Data
public class Wnrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId
	@ApiModelProperty(value = "规则ID")
	private Integer rlid;

	@ApiModelProperty(value = "指标ID")
	private Integer msid;

	@ApiModelProperty(value = "图层ID")
	private Integer lyid;

	@ApiModelProperty(value = "组合属性值ID")
	private Integer cpvid;

	@NotNull(message = "规则名称不能为空")
	@ApiModelProperty(value = "规则名称")
	private String rlnm;

	@NotNull(message = "规则内容不能为空")
	@ApiModelProperty(value = "规则内容")
	private String rlcnt;

	@ApiModelProperty(value = "[时间].年月日,[阀门].[X区].[AAA]")
	private String ftbnm;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	@NotNull(message = "规则状态不能为空")
	@ApiModelProperty(value = "-1禁用，0启用但不处理，1启用且处理")
	private Integer stat;

	@ApiModelProperty(value = "最近批次")
	private Integer lstbch;

	public static final String RLID = "rlid";

	public static final String MSID = "msid";

	public static final String LYID = "lyid";

	public static final String CPVID = "cpvid";

	public static final String RLNM = "rlnm";

	public static final String RLCNT = "rlcnt";

	public static final String FTBNM = "ftbnm";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	public static final String STAT = "stat";

	public static final String LSTBCH = "lstbch";

}
