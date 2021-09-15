package com.digitalchina.admin.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.WrnlRiskRelevance;

/**
 * <p>
 * 风险设置指标和模型参数关联表 服务类
 * </p>
 *
 * @author Ryan
 * @since 2020-01-03
 */
public interface WrnlRiskRelevanceService extends IService<WrnlRiskRelevance> {

	/**
	 * 获取风险关联指标
	 * @param id
	 * @return
	 */
	List<WrnlRiskRelevance> getMesuByRisk(Integer id);

}
