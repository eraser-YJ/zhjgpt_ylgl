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
 * 事项来源
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Data
@TableName("public.befrom")
@ApiModel(value = "Befrom对象", description = "事项来源")
public class Befrom implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "事项来源代码")
	@TableId
	private Integer efbh;

	@ApiModelProperty(value = "事项来源名称", required = true)
	@NotNull(message = "事项来源名称不能为空")
	private String efnm;

	@ApiModelProperty(value = "版本")
	private Integer rev;

	@ApiModelProperty(value = "创建时间")
	private Date crdt;

	@ApiModelProperty(value = "修改时间")
	private Date modt;

	public static final String EFBH = "efbh";

	public static final String EFNM = "efnm";

	public static final String REV = "rev";

	public static final String CRDT = "crdt";

	public static final String MODT = "modt";

	@Override
	public String toString() {
		return "Befrom{" + "efbh=" + efbh + ", efnm=" + efnm + ", rev=" + rev + ", crdt=" + crdt + ", modt=" + modt
				+ "}";
	}
}
