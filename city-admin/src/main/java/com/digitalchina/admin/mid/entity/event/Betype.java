package com.digitalchina.admin.mid.entity.event;

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
 * 事项类型
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.betype")
@ApiModel(value = "Betype对象", description = "事项类型")
public class Betype implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "事项类型代码")
	@TableId
	private Integer etbh;

	@ApiModelProperty(value = "事项类型名称", required = true)
	@NotNull(message = "事项类型名称不能为空")
	private String etnm;

	@ApiModelProperty(value = "版本")
	private Integer rev;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	public static final String ETBH = "etbh";

	public static final String ETNM = "etnm";

	public static final String REV = "rev";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	@Override
	public String toString() {
		return "Betype{" + "etbh=" + etbh + ", etnm=" + etnm + ", rev=" + rev + ", crdt=" + crdt + ", modt=" + modt
				+ "}";
	}
}
