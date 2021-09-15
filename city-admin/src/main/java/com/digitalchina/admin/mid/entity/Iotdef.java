package com.digitalchina.admin.mid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * iot组合属性关联表
 * </p>
 *
 * @author lihui
 * @since 2019-09-25
 */
@Data
@TableName("iot.iotdef")
@ApiModel(value = "Iotdef对象", description = "iot组合属性关联表")
public class Iotdef implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "组合属性")
	private String cpvs;

	@ApiModelProperty(value = "表名")
	private String tbnm;

	@ApiModelProperty(value = "组合属性ID")
	private String cpvid;

	public static final String CPVS = "cpvs";

	public static final String TBNM = "tbnm";

	public static final String CPVID = "cpvid";

	@Override
	public String toString() {
		return "Iotdef{" + "cpvs=" + cpvs + ", tbnm=" + tbnm + "}";
	}
}
