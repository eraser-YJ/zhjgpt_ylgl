package com.digitalchina.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.mapper.SysRoleMapper;
import com.digitalchina.modules.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> findRolesByUserId(Integer userId) {
        return baseMapper.findRolesByUserId(userId);
    }

    @Override
    public void defaultRole(Integer dftype, Integer roleId) {
        baseMapper.defaultRole(dftype, roleId);
    }

    @Override
    public int checkExist(Integer roleId, String code){
        return baseMapper.checkExist(roleId,code);
    }

    @Override
    public List<SysRole> findDefaultRoles() {
        return baseMapper.selectList(Condition.<SysRole>create().lambda().eq(SysRole::getDftype,1));
    }
}
