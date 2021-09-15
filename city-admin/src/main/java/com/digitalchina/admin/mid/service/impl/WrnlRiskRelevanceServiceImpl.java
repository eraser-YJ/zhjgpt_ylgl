package com.digitalchina.admin.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.WrnlRiskRelevance;
import com.digitalchina.admin.mid.mapper.WrnlRiskRelevanceMapper;
import com.digitalchina.admin.mid.service.WrnlRiskRelevanceService;

/**
 * <p>
 * 风险设置指标和模型参数关联表 服务实现类
 * </p>
 *
 * @author Ryan
 * @since 2020-01-03
 */
@Service
public class WrnlRiskRelevanceServiceImpl extends ServiceImpl<WrnlRiskRelevanceMapper, WrnlRiskRelevance> implements WrnlRiskRelevanceService {

	@Override
	public List<WrnlRiskRelevance> getMesuByRisk(Integer id) {
		return baseMapper.getMesuByRisk(id);
	}

}
