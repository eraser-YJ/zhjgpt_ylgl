package com.digitalchina.admin.mid.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 行政区划
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_admdiv")
@ApiModel(value = "Admdiv对象", description = "行政区划")
public class Admdiv implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "区划ID")
	@TableId("adid")
	private Integer adid;

	@ApiModelProperty(value = "父级区划ID")
	private Integer adpid;

	@ApiModelProperty(value = "区划名称", required = true)
	private String adnm;

	@ApiModelProperty(value = "区划全称", required = true)
	private String adflnm;

	@ApiModelProperty(value = "区划族谱")
	private Integer[] adupnms;

	@ApiModelProperty(value = "区划级别", required = true)
	private Integer adlev;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	public void setCrdt(String crdt) {
		this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
	}

	public void setModt(String modt) {
		this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
	}

	public static final String ADID = "adid";

	public static final String ADPID = "adpid";

	public static final String ADNM = "adnm";

	public static final String ADFLNM = "adflnm";

	public static final String ADLEV = "adlev";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	@Override
	public String toString() {
		return "Admdiv{" + "adid=" + adid + ", adpid=" + adpid + ", adnm=" + adnm + ", adflnm=" + adflnm + ", adlev="
				+ adlev + ", crdt=" + crdt + ", modt=" + modt + "}";
	}
}
