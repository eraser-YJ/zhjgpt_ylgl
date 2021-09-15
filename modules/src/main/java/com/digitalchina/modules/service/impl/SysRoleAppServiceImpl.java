package com.digitalchina.modules.service.impl;

import com.digitalchina.modules.entity.SysRoleApp;
import com.digitalchina.modules.mapper.SysRoleAppMapper;
import com.digitalchina.modules.service.SysRoleAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Service
public class SysRoleAppServiceImpl extends ServiceImpl<SysRoleAppMapper, SysRoleApp> implements SysRoleAppService {
    @Override
    public int selectSysCodeCnt(Integer[] roleIds, String sysCode) {
        return baseMapper.selectSysCodeCnt(roleIds, sysCode);
    }

    @Override
    public boolean isAppRoleExist(Integer roleId, String sysCode) {
        return baseMapper.countAppRole(roleId, sysCode) > 0;
    }

    @Override
    public void saveAppRole(Integer roleId, String sysCode) {
        baseMapper.saveAppRole(roleId, sysCode);
    }
}
