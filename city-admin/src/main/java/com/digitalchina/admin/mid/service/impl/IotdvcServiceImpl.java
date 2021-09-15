package com.digitalchina.admin.mid.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.Iotdvc;
import com.digitalchina.admin.mid.mapper.IotdvcMapper;
import com.digitalchina.admin.mid.service.IotdvcService;

/**
 * <p>
 * 设备 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Service
public class IotdvcServiceImpl extends ServiceImpl<IotdvcMapper, Iotdvc> implements IotdvcService {

	@Override
	public int saveOne(Iotdvc iotdvc) {
		return baseMapper.saveOne(iotdvc);
	}

	@Override
	public int updateOne(Iotdvc iotdvc) {
		return baseMapper.updateOne(iotdvc);
	}
}
