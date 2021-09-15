package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.AmSysRole;
import com.digitalchina.admin.mid.mapper.AmSysRoleMapper;
import com.digitalchina.admin.mid.service.AmSysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Service
public class AmSysRoleServiceImpl extends ServiceImpl<AmSysRoleMapper, AmSysRole> implements AmSysRoleService {

    @Override
    public List<AmSysRole> findRolesByUserId(Integer userId) {
        return baseMapper.findRolesByUserId(userId);
    }

    @Override
    public int checkExist(Integer roleId, String code) {
        return baseMapper.checkExist(roleId, code);
    }

}
