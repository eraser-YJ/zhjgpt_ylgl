package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.WrnlRiskRelevance;

/**
 * <p>
 * 风险设置指标和模型参数关联表 Mapper 接口
 * </p>
 *
 * @author Ryan
 * @since 2020-01-03
 */
public interface WrnlRiskRelevanceMapper extends BaseMapper<WrnlRiskRelevance> {

	/**
	 * 获取风险关联的指标
	 * 
	 * @param id
	 * @return
	 */
	List<WrnlRiskRelevance> getMesuByRisk(@Param("id")Integer id);

}
