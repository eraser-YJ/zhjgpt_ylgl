package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.dto.EmPlanDto;
import com.digitalchina.admin.mid.entity.emergency.EmPlan;

/**
 * <p>
 * 应急预案 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
public interface EmPlanMapper extends BaseMapper<EmPlan> {

	// 列表查看
	@SuppressWarnings("rawtypes")
	List<EmPlanDto> pageList(IPage page, @Param("appId") Integer appId, @Param("msttype") Integer msttype,
			@Param("planType") Integer planType, @Param("planName") String planName);

}
