package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.SuppliesResource;
import com.digitalchina.admin.mid.mapper.SuppliesResourceMapper;
import com.digitalchina.admin.mid.service.SuppliesResourceService;

/**
 * <p>
 * 应急救援物资管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class SuppliesResourceServiceImpl extends ServiceImpl<SuppliesResourceMapper, SuppliesResource> implements SuppliesResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		
		baseMapper.updateGis(id, lon, lat);
		
	}

}
