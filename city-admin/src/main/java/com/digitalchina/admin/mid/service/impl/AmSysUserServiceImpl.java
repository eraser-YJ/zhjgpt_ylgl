package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.AmSysUser;
import com.digitalchina.admin.mid.mapper.AmSysUserMapper;
import com.digitalchina.admin.mid.service.AmSysUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Service
public class AmSysUserServiceImpl extends ServiceImpl<AmSysUserMapper, AmSysUser> implements AmSysUserService {

    @Override
    public int checkExist(Integer roleId, String code) {
        return baseMapper.checkExist(roleId, code);
    }
}
