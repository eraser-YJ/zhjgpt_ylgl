package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.RoleMenuDto;
import com.digitalchina.admin.mid.entity.AmSysRoleMenu;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cwc
 * @since 2019-08-30
 */
public interface AmSysRoleMenuService extends IService<AmSysRoleMenu> {

    /**
     * 角色菜单授权
     *
     * @param roleMenuDto
     */
    void authMenu(RoleMenuDto roleMenuDto);
}
