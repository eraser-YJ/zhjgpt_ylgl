package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.EmPlanDto;
import com.digitalchina.admin.mid.entity.emergency.EmPlan;

/**
 * <p>
 * 应急预案 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
public interface EmPlanService extends IService<EmPlan> {

	// 查看列表
	@SuppressWarnings("rawtypes")
	IPage<EmPlanDto> pageList(IPage page, Integer appId, Integer msttype, Integer planType, String planName);

}
