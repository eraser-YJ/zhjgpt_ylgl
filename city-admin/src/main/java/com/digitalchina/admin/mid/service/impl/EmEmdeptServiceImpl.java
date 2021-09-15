package com.digitalchina.admin.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.emergency.EmEmdept;
import com.digitalchina.admin.mid.mapper.EmEmdeptMapper;
import com.digitalchina.admin.mid.service.EmEmdeptService;

/**
 * <p>
 * 应急部门 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
@Service
public class EmEmdeptServiceImpl extends ServiceImpl<EmEmdeptMapper, EmEmdept> implements EmEmdeptService {

	@Override
	public List<EmEmdept> choose() {
		
		List<EmEmdept> list = baseMapper.choose();
		
		return list;
	}

}
