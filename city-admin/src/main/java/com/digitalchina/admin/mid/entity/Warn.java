package com.digitalchina.admin.mid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 警告
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn")
@ApiModel(value = "Warn对象", description = "警告")
public class Warn implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "警告ID")
	@TableId
	private Integer wnid;

	@ApiModelProperty(value = "传感器ID")
	private Integer idsid;

	@ApiModelProperty(value = "区划ID")
	private Integer adid;

	@ApiModelProperty(value = "设备ID")
	private Integer idid;

	@ApiModelProperty(value = "指标ID")
	private Integer msid;

	@ApiModelProperty(value = "规则ID")
	private Integer rlid;

	@ApiModelProperty(value = "图层ID")
	private Integer lyid;

	@ApiModelProperty(value = "警告名称")
	private String wnnm;

	@ApiModelProperty(value = "警告内容")
	private String wncnt;

	@ApiModelProperty(value = "预警报警异常")
	private Integer wncty;

	@ApiModelProperty(value = "警告状态")
	private Integer wnstat;

	@ApiModelProperty(value = "发布发送")
	private Integer pubmit;

	@ApiModelProperty(value = "坐标")
	private String wnxy;

	@ApiModelProperty(value = "次数")
	private Integer wnnum;

	@ApiModelProperty(value = "来源种类")
	private Integer srctp;

	@ApiModelProperty(value = "委办局名称")
	private String cmnnm;

	@ApiModelProperty(value = "属性数")
	private Integer cpnum;

	@ApiModelProperty(value = "属性值")
	private String cpvs;

	@ApiModelProperty(value = "参考SQL")
	private String refsqls;

	@ApiModelProperty(value = "参考SQL说明")
	private String refnms;

	@ApiModelProperty(value = "数据时间")
	private Date dgdt;

	@ApiModelProperty(value = "非TS数据时间")
	private String dgdt2;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "初次查看时间")
	private Date vwdt;

	@ApiModelProperty(value = "开始处理时间")
	private Date spdt;

	@ApiModelProperty(value = "结束处理时间")
	private Date epdt;

	public static final String WNID = "wnid";

	public static final String IDSID = "idsid";

	public static final String ADID = "adid";

	public static final String IDID = "idid";

	public static final String MSID = "msid";

	public static final String RLID = "rlid";

	public static final String LYID = "lyid";

	public static final String WNNM = "wnnm";

	public static final String WNCNT = "wncnt";

	public static final String WNCTY = "wncty";

	public static final String WNSTAT = "wnstat";

	public static final String PUBMIT = "pubmit";

	public static final String WNXY = "wnxy";

	public static final String WNNUM = "wnnum";

	public static final String SRCTP = "srctp";

	public static final String CMNNM = "cmnnm";

	public static final String CPNUM = "cpnum";

	public static final String CPVS = "cpvs";

	public static final String REFSQLS = "refsqls";

	public static final String REFNMS = "refnms";

	public static final String DGDT = "dgdt";

	public static final String DGDT2 = "dgdt2";

	public static final String CRDT = "crdt";

	public static final String VWDT = "vwdt";

	public static final String SPDT = "spdt";

	public static final String EPDT = "epdt";

	@Override
	public String toString() {
		return "Warn{" + "wnid=" + wnid + ", idsid=" + idsid + ", idid=" + idid + ", msid=" + msid + ", rlid=" + rlid + ", lyid=" + lyid + ", wnnm=" + wnnm + ", wncnt=" + wncnt + ", wncty=" + wncty + ", wnstat=" + wnstat + ", pubmit=" + pubmit + ", wnxy=" + wnxy + ", wnnum=" + wnnum + ", srctp=" + srctp + ", cmnnm=" + cmnnm + ", cpnum=" + cpnum + ", cpvs=" + cpvs + ", refsqls=" + refsqls + ", refnms=" + refnms + ", dgdt=" + dgdt + ", dgdt2=" + dgdt2 + ", crdt=" + crdt + ", vwdt=" + vwdt + ", spdt=" + spdt + ", epdt=" + epdt + "}";
	}
}
