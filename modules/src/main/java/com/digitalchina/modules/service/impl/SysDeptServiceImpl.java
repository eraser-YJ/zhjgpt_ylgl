package com.digitalchina.modules.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.dto.BedeptDto;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.mapper.SysDeptMapper;
import com.digitalchina.modules.service.SysDeptService;

/**
 * <p>
 * 统一部门 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2020-03-24
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

	@Override
	public void removeTreeById(Integer bedid) {
		baseMapper.removeTreeById(bedid);
	}

	@Override
	public List<BedeptDto> tree() {
		QueryWrapper<SysDept> cd = Condition.<SysDept>create().orderByAsc(SysDept.BDTYPE).orderByAsc(SysDept.DPID);
		List<SysDept> list = this.list(cd);
		List<BedeptDto> dto = list.stream().map(item -> new BedeptDto(item)).collect(Collectors.toList());
		return BedeptDto.makeTree(dto);
	}
}
