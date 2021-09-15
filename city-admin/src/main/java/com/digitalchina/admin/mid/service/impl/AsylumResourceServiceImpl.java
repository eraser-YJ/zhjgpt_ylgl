package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.AsylumResource;
import com.digitalchina.admin.mid.mapper.AsylumResourceMapper;
import com.digitalchina.admin.mid.service.AsylumResourceService;

/**
 * <p>
 * 应急避难场所管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class AsylumResourceServiceImpl extends ServiceImpl<AsylumResourceMapper, AsylumResource> implements AsylumResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		
		baseMapper.updateGis(id, lon, lat);

		
	}

}
