package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.AmMenuDto;
import com.digitalchina.admin.mid.entity.AmSysMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysMenuService extends IService<AmSysMenu> {

    /**
     * 查询用户菜单
     *
     * @param userId 用户菜单
     * @return
     */
    List<AmMenuDto> findUserMenu(Integer userId);

    List<AmMenuDto> findRoleMenu(Integer roleId);
}
