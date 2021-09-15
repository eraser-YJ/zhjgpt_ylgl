package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.RescueResource;
import com.digitalchina.admin.mid.mapper.RescueResourceMapper;
import com.digitalchina.admin.mid.service.RescueResourceService;

/**
 * <p>
 * 应急救援队伍管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class RescueResourceServiceImpl extends ServiceImpl<RescueResourceMapper, RescueResource> implements RescueResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		
		baseMapper.updateGis(id, lon, lat);
		
	}

}
