package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.VehicleResource;
import com.digitalchina.admin.mid.mapper.VehicleResourceMapper;
import com.digitalchina.admin.mid.service.VehicleResourceService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class VehicleResourceServiceImpl extends ServiceImpl<VehicleResourceMapper, VehicleResource> implements VehicleResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		
		baseMapper.updateGis(id, lon, lat);
		
	}

}
