package com.digitalchina.admin.mid.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.dto.RoleMenuDto;
import com.digitalchina.admin.mid.entity.AmSysRoleMenu;
import com.digitalchina.admin.mid.mapper.AmSysRoleMenuMapper;
import com.digitalchina.admin.mid.service.AmSysRoleMenuService;
import com.digitalchina.modules.constant.TransConstant;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cwc
 * @since 2019-08-30
 */
@Service
public class AmSysRoleMenuServiceImpl extends ServiceImpl<AmSysRoleMenuMapper, AmSysRoleMenu>
		implements AmSysRoleMenuService {

	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	@Override
	public void authMenu(RoleMenuDto roleMenuDto) {
		// 先删除原来的
		this.remove(Condition.<AmSysRoleMenu>create().eq(AmSysRoleMenu.RID, roleMenuDto.getRoleId()));
		if (CollectionUtil.isNotEmpty(roleMenuDto.getMenuIdList())) {
			List<AmSysRoleMenu> list = roleMenuDto.getMenuIdList().stream()
					.map(item -> new AmSysRoleMenu(roleMenuDto.getRoleId(), item)).collect(Collectors.toList());
			for (AmSysRoleMenu a : list) {
				this.save(a);
			}
		}
	}
}
