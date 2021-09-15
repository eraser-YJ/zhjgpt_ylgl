package com.digitalchina.modules.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.dto.SysDictDto;
import com.digitalchina.modules.entity.SysDict;
import com.digitalchina.modules.entity.SysDictItem;
import com.digitalchina.modules.mapper.SysDictItemMapper;
import com.digitalchina.modules.service.SysDictItemService;
import com.digitalchina.modules.service.SysDictService;

import cn.hutool.core.collection.CollUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2020-03-19
 */
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

	@Autowired
	private SysDictService sds;

	@Override
	public List<SysDictDto> findByCode(String code) {
		List<SysDictDto> result = new ArrayList<>();
		if (sds.count(Condition.<SysDict>create().eq(SysDict.STATUS, SysDict.STATUS_ENABLE)) <= 0) {
			return result;
		}
		List<SysDictItem> list = this.baseMapper.selectList(Condition.<SysDictItem>create()
				.select(SysDictItem.ITEM_NAME, SysDictItem.ITEM_VALUE).eq(SysDictItem.DICT_CODE, code)
				.eq(SysDictItem.STATUS, SysDictItem.STATUS_ENABLE).orderByAsc(SysDictItem.SORT));
		if (CollUtil.isNotEmpty(list)) {
			return list.stream().map(item -> {
				return new SysDictDto(item.getItemName(), item.getItemValue());
			}).collect(Collectors.toList());
		}
		return result;
	}

}
