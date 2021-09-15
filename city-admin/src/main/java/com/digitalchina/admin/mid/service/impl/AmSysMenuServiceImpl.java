package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.dto.AmMenuDto;
import com.digitalchina.admin.mid.entity.AmSysMenu;
import com.digitalchina.admin.mid.entity.AmSysRoleMenu;
import com.digitalchina.admin.mid.mapper.AmSysMenuMapper;
import com.digitalchina.admin.mid.service.AmSysMenuService;
import com.digitalchina.admin.mid.service.AmSysRoleMenuService;
import com.digitalchina.modules.constant.enums.StateEnum;
import com.digitalchina.modules.dto.MenuDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@Service
public class AmSysMenuServiceImpl extends ServiceImpl<AmSysMenuMapper, AmSysMenu> implements AmSysMenuService {

    @Autowired
    private AmSysRoleMenuService rolemenuService;

    @Override
    public List<AmMenuDto> findUserMenu(Integer userId) {
        List<AmSysMenu> menusList = baseMapper.findUserMenu(userId);
        return AmMenuDto.makeTree(menusList.stream().map(item -> new AmMenuDto(item)).collect(Collectors.toList()));
    }

    @Override
    public List<AmMenuDto> findRoleMenu(Integer roleId) {
        List<AmSysMenu> menuList = baseMapper.selectList(Condition.<AmSysMenu>create()
                .eq(AmSysMenu.STATUS, StateEnum.ACTIVE.getCode()).orderByAsc(AmSysMenu.SORT));

        List<AmSysRoleMenu> roleMenuList = rolemenuService.list(Condition.<AmSysRoleMenu>create().eq(AmSysRoleMenu.RID, roleId));
        Set<Integer> menuIds = roleMenuList.stream().map(AmSysRoleMenu::getMid).collect(Collectors.toSet());

        for (AmSysMenu menu : menuList) {
            menu.setState(menuIds.contains(menu.getId()) ? StateEnum.ACTIVE.getCode() : StateEnum.DISABLE.getCode());
        }

        return MenuDto.makeTree(menuList.stream().map(item -> new AmMenuDto(item)).collect(Collectors.toList()));
    }
}
