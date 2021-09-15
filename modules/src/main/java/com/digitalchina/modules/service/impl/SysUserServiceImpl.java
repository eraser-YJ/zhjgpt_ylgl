package com.digitalchina.modules.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.digitalchina.modules.service.SysRoleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.mapper.SysRoleUserMapper;
import com.digitalchina.modules.mapper.SysUserMapper;
import com.digitalchina.modules.service.SysUserService;

import cn.hutool.core.collection.CollUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	@Autowired
	private SysRoleUserMapper roleUserMapper;

	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public SysUser findInfo(Integer userId) {
		return baseMapper.findInfo(userId);
	}

	@Override
	public List<SysRole> findRole(Integer userId) {
		return baseMapper.findRole(userId);
	}

	@Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
	@Override
	public void empowerRole(Integer[] roleIds, Integer userId) {
		List<Integer> roleList = CollUtil.newArrayList(roleIds);
		List<SysRoleUser> roleUserList = new ArrayList<>();
		for (Integer id : roleList) {
			roleUserList.add(new SysRoleUser(userId, id));
		}
		roleUserMapper.delete(Condition.<SysRoleUser>create().eq(SysRoleUser.UID, userId));
		for (SysRoleUser sysRoleUser : roleUserList) {
			roleUserMapper.insert(sysRoleUser);
		}
	}

	@Override
	public IPage<SysUser> findUserListBycode(IPage page, String code, String keywords) {
		return page.setRecords(baseMapper.findUserListBycode(page, code, keywords));
	}

    @Override
    public SysUser loadUserRoleById(Integer userId) {
        val user = getById(userId);
		user.setRoleList(sysRoleService.findRolesByUserId(userId));
		return user;
    }

	@Override
	public SysUser getUserBySsoid(String ssoid) {
		return baseMapper.selectOne(Condition.<SysUser>create().eq(SysUser.SSOID, ssoid));
	}
}
