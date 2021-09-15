package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.PoliceResource;
import com.digitalchina.admin.mid.mapper.PoliceResourceMapper;
import com.digitalchina.admin.mid.service.PoliceResourceService;

/**
 * <p>
 * 警力资源管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class PoliceResourceServiceImpl extends ServiceImpl<PoliceResourceMapper, PoliceResource> implements PoliceResourceService {

	@Override
	public void updateGis(Integer id,String lon,String lat) {
		baseMapper.updateGis(id,lon,lat);
		
	}

	
	
}
