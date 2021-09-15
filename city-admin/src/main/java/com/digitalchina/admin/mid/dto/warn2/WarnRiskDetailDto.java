package com.digitalchina.admin.mid.dto.warn2;

import java.util.List;

import com.digitalchina.admin.mid.entity.WrnlRisk;
import com.digitalchina.admin.mid.entity.WrnlRiskRelevance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <预警风险明细>
 * 
 * @author lihui
 * @since 2020年1月2日
 */
@ApiModel("预警风险明细")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarnRiskDetailDto {

	private Integer id;

	@ApiModelProperty(value = "预警模型ID")
	private Integer modelid;

	@ApiModelProperty(value = "频率")
	private String frequency;

	@ApiModelProperty("天（间隔天数）")
	private Integer intervaldays;

	@ApiModelProperty("指定每月几号（1~31）")
	private Integer[] fixdays;

	@ApiModelProperty("周（星期一~星期日：1~7）")
	private Integer[] week;

	@ApiModelProperty("月（间隔月数）")
	private Integer month;

	@ApiModelProperty("时间（实时：空时间，定时/时间段：无逗号/逗号隔开）")
	private String time;

	@ApiModelProperty(value = "部门列表")
	List<WarnRiskDeptDto> depts;

	@ApiModelProperty(value = "关联关系")
	List<WrnlRiskRelevance> relevances;
	
	@ApiModelProperty(value = "指标(仅展示)")
	List<WarnMesuDetailDto> mesus;

	public WarnRiskDetailDto(WrnlRisk s) {
		this.id = s.getId();
		this.modelid = s.getId();
		this.frequency = s.getFrequency();
	}

}
