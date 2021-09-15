package com.digitalchina.admin.mid.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.digitalchina.admin.utils.PgGisTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Data
@TableName("warn.warn_iotdvc")
@ApiModel(value = "Iotdvc对象", description = "设备")
@NoArgsConstructor
@AllArgsConstructor
public class Iotdvc implements Serializable {

	private static final long serialVersionUID = 1L;

	public Iotdvc(Gis gis) {
		this.wnxy = gis;
	}

	@ApiModelProperty(value = "设备ID")
	@TableId("idid")
	private Integer idid;

	@ApiModelProperty(value = "委办局ID")
	private Integer cmnid;

	@ApiModelProperty(value = "区划ID")
	private Integer adid;

	@ApiModelProperty(value = "组合属性值ID")
	private Integer cpvid;

	@ApiModelProperty(value = "图层ID")
	private Integer lyid;

	@ApiModelProperty(value = "设备名称")
	private String idnm;

	@ApiModelProperty(value = "设备标识")
	private String idmac;

	@ApiModelProperty(value = "设备编号")
	private String idnum;

	/**
	 * 坐标对象,需要转换成数据库坐标对象和大地坐标系, 隐式在typeHandler和sqlHandler中执行 不需要关注这块逻辑
	 * ps:不同地图采用坐标系不同,需要对应
	 * 
	 * @see PgGisTypeHandler
	 */
	@TableField(value = "ST_AsGeoJSON  ( st_transform(wnxy, 4326 ) ) :: JSON")
	@ApiModelProperty(value = "坐标", required = true)
	private Gis wnxy;

	@ApiModelProperty(value = "设备位置")
	private String idaddr;

	@ApiModelProperty(value = "干道")
	private String road;

	@ApiModelProperty(value = "重点区域")
	private String iparea;

	@ApiModelProperty(value = "设备类型")
	private Integer idtp;

	@ApiModelProperty(value = "url")
	private String url;

	@ApiModelProperty(value = "异常启用（0-不启用 1-启用）")
	private Boolean idexp;

	@ApiModelProperty(value = "异常阈值")
	private Integer iditv;

	@ApiModelProperty(value = "异常状态")
	private Integer idstat;

	@ApiModelProperty(value = "维护人")
	private String idadmin;

	@ApiModelProperty(value = "维护电话")
	private String idtel;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	@ApiModelProperty(value = "区控")
	private String fnnote;

	@ApiModelProperty(value = "执行标准")
	private String stnd;

	@ApiModelProperty(value = "权属")
	private String[] owner;
	
	@TableField(exist = false)
	@ApiModelProperty(value = "指标")
	private String menames;


	public void setCrdt(String crdt) {
		this.crdt = StrUtil.isEmpty(crdt) ? new Date() : DateUtil.parse(crdt);
	}

	public void setModt(String modt) {
		this.modt = StrUtil.isEmpty(modt) ? new Date() : DateUtil.parse(modt);
	}

	public static final String IDID = "idid";

	public static final String CMNID = "cmnid";

	public static final String ADID = "adid";

	public static final String CPVID = "cpvid";

	public static final String LYID = "lyid";

	public static final String IDNM = "idnm";

	public static final String IDMAC = "idmac";

	public static final String IDNUM = "idnum";

	public static final String WNXY = "wnxy";

	public static final String IDADDR = "idaddr";

	public static final String ROAD = "road";

	public static final String IPAREA = "iparea";

	public static final String IDTP = "idtp";

	public static final String URL = "url";

	public static final String IDEXP = "idexp";

	public static final String IDITV = "iditv";

	public static final String IDSTAT = "idstat";

	public static final String IDADMIN = "idadmin";

	public static final String IDTEL = "idtel";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

}
