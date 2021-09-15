package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.dto.MenuDto;
import com.digitalchina.modules.dto.MenuListDto;
import com.digitalchina.modules.entity.SysMenu;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查找用户菜单
     *
     * @param userId 用户ID
     * @param code   系统code
     * @return
     */
    List<MenuDto> findUserMenu(Integer userId, String code);

    /**
     * 查找全量菜单
     *
     * @param code   系统code
     * @return
     */
    List<MenuDto> findUserAllMenu(String code);

    /**
     * @description 修改菜单状态
     * @author cwc
     * @date 2019/8/29 16:08
     * @params dto : 菜单状态对象
     **/
    void statusMenu(Integer menuId, Integer status, String appCode);

    /**
     * @description 更新整个菜单树
     * @author cwc
     * @date 2019/8/29 17:42
     * @params menuList : 菜单集合
     **/
    void updateMenuSort(List<SysMenu> menuList);

    /**
     * @description 查询当前角色的菜单列表
     * @author cwc
     * @date 2019/8/30 12:47
     * @params roleId : 角色ID
     **/
    List<MenuDto> getRoleMenu(Integer roleId);

    /**
     * @description 设置当前角色的菜单列表状态
     * @author cwc
     * @date 2019/8/30 12:54
     * @params menuListDto :
     **/
    void statusRoleMenu(MenuListDto menuListDto);
}
