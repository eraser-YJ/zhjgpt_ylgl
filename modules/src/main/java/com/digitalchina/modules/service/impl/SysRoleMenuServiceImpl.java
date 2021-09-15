package com.digitalchina.modules.service.impl;

import com.digitalchina.modules.entity.SysRoleMenu;
import com.digitalchina.modules.mapper.SysRoleMenuMapper;
import com.digitalchina.modules.service.SysRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private  SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRoleMenu> selectMidMenu(Integer uid) {
        return sysRoleMenuMapper.selectMidMenu(uid);
    }

    @Override
    public List<SysRoleMenu> selectNoticeRole(Integer uid) {
        return sysRoleMenuMapper.selectNoticeRole(uid);
    }
}
