package com.digitalchina.admin.mid.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 指标
 * </p>
 *
 * @author lichunlong
 * @since 2019-08-30
 */
@Data
@TableName("warn.warn_mesu")
@ApiModel(value = "Mesu对象", description = "指标")
public class Mesu implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "指标ID")
	@TableId("msid")
	private Integer msid;

	@ApiModelProperty(value = "图层ID")
	private Integer lyid;

	@ApiModelProperty(value = "指标代码")
	private String mscode;

	@ApiModelProperty(value = "指标名称")
	private String msnm;

	@ApiModelProperty(value = "指标单位")
	private String msunit;

	@ApiModelProperty(value = "-1禁用，0启用但不处理，1启用且处理")
	private Integer stat;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	@ApiModelProperty(value = "数据类型")
	private String type;

	@TableField(exist = false)
	@ApiModelProperty(value = "备注")
	private String remark;

	public String getRemark() {
		String unit = StrUtil.isEmpty(this.msunit) ? "无" : this.msunit;
		this.remark = "单位：" + unit;
		return remark;
	}

	public void setCrdt(String crdt) {
		this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
	}

	public void setModt(String modt) {
		this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
	}

	public static final String MSID = "msid";

	public static final String LYID = "lyid";

	public static final String MSCODE = "mscode";

	public static final String MSNM = "msnm";

	public static final String MSUNIT = "msunit";

	public static final String STAT = "stat";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	@Override
	public String toString() {
		return "Mesu{" + "msid=" + msid + ", lyid=" + lyid + ", mscode=" + mscode + ", msnm=" + msnm + ", msunit="
				+ msunit + ", stat=" + stat + ", crdt=" + crdt + ", modt=" + modt + "}";
	}
}
