package com.digitalchina.modules.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.mapper.SysRoleUserMapper;
import com.digitalchina.modules.service.SysRoleUserService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Service
public class SysRoleUserServiceImpl extends ServiceImpl<SysRoleUserMapper, SysRoleUser> implements SysRoleUserService {

	@Override
	public List<SysUser> getAllByRole(Page<SysUser> page, Integer rid, String uname) {
		return baseMapper.getAllByRole(page, rid, uname);
	}

	@Override
	public List<SysUser> getAllByRole(Integer rid, String uname) {
		return baseMapper.getAllByRole(rid, uname);
	}
}
