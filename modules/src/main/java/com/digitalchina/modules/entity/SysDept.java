package com.digitalchina.modules.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 统一部门
 * </p>
 *
 * @author Warrior
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
@ApiModel(value = "SysDept对象", description = "统一部门")
@NoArgsConstructor
public class SysDept implements Serializable {

	public SysDept(Integer bedid, String bdnm) {
		super();
		this.dpid = bedid;
		this.bdnm = bdnm;
	}

	private static final long serialVersionUID = 1L;

	@TableId
	@ApiModelProperty(value = "部门ID")
	private Integer dpid;

	@ApiModelProperty(value = "部门名称")
	private String bdnm;

	@ApiModelProperty(value = "上级部门ID")
	private Integer bdpntid;

	@ApiModelProperty(value = "上级部门ID清单")
	private Integer[] bdpntids;

	@ApiModelProperty(value = "部门类型(备用)")
	private Integer bdtype;

	@ApiModelProperty(value = "创建人")
	private Integer cruid;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改人")
	private Integer mouid;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	public static final String DPID = "dpid";

	public static final String BDNM = "bdnm";

	public static final String BDPNTID = "bdpntid";

	public static final String BDPNTIDS = "bdpntids";

	public static final String BDTYPE = "bdtype";

	public static final String CRUID = "cruid";

	public static final String CRDT = "crdt";

	public static final String MOUID = "mouid";

	public static final String MODT = "modt";

}
