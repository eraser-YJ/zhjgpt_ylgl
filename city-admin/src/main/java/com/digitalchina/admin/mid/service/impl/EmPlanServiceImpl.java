package com.digitalchina.admin.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.dto.EmPlanDto;
import com.digitalchina.admin.mid.entity.emergency.EmPlan;
import com.digitalchina.admin.mid.mapper.EmPlanMapper;
import com.digitalchina.admin.mid.service.EmPlanService;

/**
 * <p>
 * 应急预案 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
@Service
public class EmPlanServiceImpl extends ServiceImpl<EmPlanMapper, EmPlan> implements EmPlanService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPage<EmPlanDto> pageList(IPage page, Integer appId, Integer msttype, Integer planType, String planName) {

		List<EmPlanDto> pageList = baseMapper.pageList(page, appId, msttype, planType, planName);

		return page.setRecords(pageList);
	}

}
