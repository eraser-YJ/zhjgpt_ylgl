package com.digitalchina.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.StateEnum;
import com.digitalchina.modules.dto.MenuDto;
import com.digitalchina.modules.dto.MenuListDto;
import com.digitalchina.modules.entity.SysApp;
import com.digitalchina.modules.entity.SysMenu;
import com.digitalchina.modules.entity.SysRoleApp;
import com.digitalchina.modules.entity.SysRoleMenu;
import com.digitalchina.modules.mapper.SysMenuMapper;
import com.digitalchina.modules.service.SysAppService;
import com.digitalchina.modules.service.SysMenuService;
import com.digitalchina.modules.service.SysRoleAppService;
import com.digitalchina.modules.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleAppService sysRoleAppService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysAppService sysAppService;

    @Override
    public List<MenuDto> findUserMenu(Integer userId, String code) {
        List<SysMenu> menusList = baseMapper.findUserMenu(userId, code);
        return MenuDto.makeTree(menusList.stream().map(item -> new MenuDto(item)).collect(Collectors.toList()));
    }

    @Override
    public List<MenuDto> findUserAllMenu( String code) {
        List<SysMenu> menusList = baseMapper.findUserAllMenu(code);
        return MenuDto.makeTree(menusList.stream().map(item -> new MenuDto(item)).collect(Collectors.toList()));
    }

    @Override
    public void statusMenu(Integer menuId, Integer status, String appCode) {
        baseMapper.statusMenu(menuId, status, appCode);
    }

    @Override
    public void updateMenuSort(List<SysMenu> menuList) {
        this.saveOrUpdateBatch(menuList);
    }

    @Override
    public List<MenuDto> getRoleMenu(Integer roleId) {

        List<SysApp> list = sysAppService.list(null);
        List<SysRoleApp> roleAppList = sysRoleAppService.list(Condition.<SysRoleApp>create().eq(SysRoleApp.RID, roleId));
        List<MenuDto> result = new ArrayList<>();

        for (SysApp app : list) {
            List<SysMenu> lists = baseMapper.getRoleMenu(roleId, StateEnum.ACTIVE.getCode(), app.getCode());
            MenuDto menuDto = new MenuDto();
            menuDto.setAppcode(app.getCode());
            menuDto.setName(app.getName());
            menuDto.setChildren(MenuDto.makeTree(lists.stream().map(item -> new MenuDto(item)).collect(Collectors.toList())));
            menuDto.setAid(app.getId());
            menuDto.setState(StateEnum.DISABLE.getCode());
            for (SysRoleApp sysRoleApp : roleAppList) {
                if (app.getId().equals(sysRoleApp.getAid())) {
                    menuDto.setState(StateEnum.ACTIVE.getCode());
                    break;
                }
            }

            Integer state = StateEnum.DISABLE.getCode();
            //有菜单权限，必有系统权限
            for (SysMenu sysMenu : lists) {
                if (StateEnum.ACTIVE.getCode().equals(sysMenu.getState())) {
                    state = StateEnum.ACTIVE.getCode();
                    break;
                }
            }
            //没菜单权限也可能有系统权限
            if(state.equals(StateEnum.DISABLE.getCode())){
                int count = sysRoleAppService.count(Condition.<SysRoleApp>create().lambda()
                        .eq(SysRoleApp::getAid, app.getId())
                        .eq(SysRoleApp::getRid, roleId));
                state = count > 0 ? StateEnum.ACTIVE.getCode():StateEnum.DISABLE.getCode();
            }
            menuDto.setState(state);
            result.add(menuDto);
        }
        return result;
    }

    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    @Override
    public void statusRoleMenu(MenuListDto menuListDto) {
        Integer roleId = menuListDto.getRoleId();
        List<SysMenu> list = menuListDto.getMenuList();
        List<Integer> roleAppIdlist = list.stream().filter(sysMenu -> StateEnum.ACTIVE.getCode().equals(sysMenu.getState()) && sysMenu.getAid() != null)
                .map(SysMenu::getAid).distinct().collect(Collectors.toList());
        List<Integer> roleMenuIdlist = list.stream().filter(sysMenu -> StateEnum.ACTIVE.getCode().equals(sysMenu.getState()) && sysMenu.getId() != null)
                .map(SysMenu::getId).collect(Collectors.toList());

        List<SysRoleApp> sysRoleApps = new ArrayList<>();
        for (Integer appId : roleAppIdlist) {
            sysRoleApps.add(new SysRoleApp(roleId, appId));
        }
        sysRoleAppService.remove(Condition.<SysRoleApp>create().eq(SysRoleApp.RID, roleId));
        sysRoleAppService.saveBatch(sysRoleApps);

        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        for (Integer menuId : roleMenuIdlist) {
            sysRoleMenus.add(new SysRoleMenu(roleId, menuId));
        }
        sysRoleMenuService.remove(Condition.<SysRoleMenu>create().eq(SysRoleMenu.RID, roleId));
        sysRoleMenuService.saveBatch(sysRoleMenus);
    }
}
