package com.digitalchina.admin.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.EmEtype;
import com.digitalchina.admin.mid.mapper.EmEtypeMapper;
import com.digitalchina.admin.mid.service.EmEtypeService;

/**
 * <p>
 * 应急事件类型 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
@Service
public class EmEtypeServiceImpl extends ServiceImpl<EmEtypeMapper, EmEtype> implements EmEtypeService {

	@Override
	public List<EmEtype> choose() {
		
		List<EmEtype> list = baseMapper.choose();
		
		return list;
	}

}
