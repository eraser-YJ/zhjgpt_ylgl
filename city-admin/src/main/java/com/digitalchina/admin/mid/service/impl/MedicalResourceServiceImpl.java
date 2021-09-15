package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.MedicalResource;
import com.digitalchina.admin.mid.mapper.MedicalResourceMapper;
import com.digitalchina.admin.mid.service.MedicalResourceService;

/**
 * <p>
 * 医疗卫生资源管理 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class MedicalResourceServiceImpl extends ServiceImpl<MedicalResourceMapper, MedicalResource> implements MedicalResourceService {

	@Override
	public void updateGis(Integer id, String lon, String lat) {
		baseMapper.updateGis(id,lon,lat);	
		
	}

}
