package com.digitalchina.admin.mid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.modules.constant.enums.SourceTypeEnum;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 风险设置指标和模型参数关联表
 * </p>
 *
 * @author Ryan
 * @since 2020-01-03
 */
@Data
@TableName("warn.warn_wrnl_risk_relevance")
@ApiModel(value = "WrnlRiskRelevance对象", description = "风险设置指标和模型参数关联表")
public class WrnlRiskRelevance implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ApiModelProperty(value = "风险ID")
	private Integer riskid;

	@ApiModelProperty(value = "模型参数ID")
	private Integer paramid;

	@ApiModelProperty(value = "组件、监测点ID")
	private Integer itemid;
	
	/**
	 * @see SourceTypeEnum
	 */
	@ApiModelProperty(value = "1组件、0监测点")
	private Integer type;

	@ApiModelProperty(value = "指标code")
	private String mscode;
	
	@ApiModelProperty(value = "指标id")
	private Integer msid;

	@ApiModelProperty(value = "组件、监测点名称")
	@TableField(exist = false)
	private String itemname;
	@ApiModelProperty(value = "指标数据类型")
	@TableField(exist = false)
	private String mesutype;
	@ApiModelProperty(value = "指标单位")
	@TableField(exist = false)
	private String msunit;
	@ApiModelProperty(value = "指标描述")
	@TableField(exist = false)
	private String msremark;

	public String getRemark() {
		String unit = StrUtil.isEmpty(this.msunit) ? "无" : this.msunit;
		msremark = "单位：" + unit;
		return msremark;
	}

	public static final String ID = "id";

	public static final String RISKID = "riskid";

	public static final String PARAMID = "paramid";

	public static final String ITEMID = "itemid";

	public static final String TYPE = "type";

	public static final String MSCODE = "mscode";
	
	public static final String MSID = "msid";

}
