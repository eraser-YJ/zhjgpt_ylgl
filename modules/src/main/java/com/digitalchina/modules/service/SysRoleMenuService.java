package com.digitalchina.modules.service;

import com.digitalchina.modules.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    List<SysRoleMenu> selectMidMenu(Integer uid);

    List<SysRoleMenu> selectNoticeRole(Integer uid);
}
