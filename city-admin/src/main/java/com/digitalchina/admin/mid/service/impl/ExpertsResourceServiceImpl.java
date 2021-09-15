package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.ExpertsResource;
import com.digitalchina.admin.mid.mapper.ExpertsResourceMapper;
import com.digitalchina.admin.mid.service.ExpertsResourceService;

/**
 * <p>
 * 专家资料管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class ExpertsResourceServiceImpl extends ServiceImpl<ExpertsResourceMapper, ExpertsResource> implements ExpertsResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		
		baseMapper.updateGis(id, lon, lat);
		
	}

}
