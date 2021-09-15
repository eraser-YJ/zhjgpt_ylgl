package com.digitalchina.admin.mid.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.log.AccessLogger;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.dto.MenuDto;
import com.digitalchina.modules.dto.MenuListDto;
import com.digitalchina.modules.entity.SysMenu;
import com.digitalchina.modules.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 菜单管理
 * @author: cwc
 * @date: 2019/8/29 10:47
 **/
@RestController
@RequestMapping("/sysMenu")
//@Authorize
@Api(tags = "系统权限管理-菜单管理")
public class SysMenuController {
    @Autowired
    private SysMenuService menuService;

    @ApiOperation(value = "创建或者更新菜单")
    @AccessLogger("创建或者更新菜单名称为：#{#menu.name} 的系统菜单")
    @PostMapping(value = "saveorupdate")
    public RespMsg<Void> saveOrUpdate(@RequestBody SysMenu menu) {
        menuService.saveOrUpdate(menu);
        return RespMsg.ok();
    }

    @ApiOperation(value = "删除菜单")
    @AccessLogger("删除系统编码：#{#appCode},菜单ID:#{#menuId}的系统菜单")
    @GetMapping("delete")
    @ApiImplicitParams({@ApiImplicitParam(name = "menuId", value = "菜单ID", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "appCode", value = "系统编码", dataType = "String", required = true)})
    public RespMsg<Void> deleteMenu(@RequestParam(required = true) Integer menuId) {
        menuService.remove(Condition.<SysMenu>create().eq(SysMenu.ID, menuId)
                .or().eq(SysMenu.PID, menuId));
        return RespMsg.ok();
    }

    @ApiOperation(value = "修改菜单状态")
    @AccessLogger("修改系统编码：#{#appCode},菜单ID：#{#menuId}的系统菜单状态为#{#status}")
    @PostMapping(value = "statusMenu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiImplicitParams({@ApiImplicitParam(name = "menuId", value = "菜单ID", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "status", value = "0：激活  1：禁止", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "appCode", value = "系统编码", dataType = "String", required = true)})
    public RespMsg<Void> statusMenu(@RequestParam(required = true) Integer menuId,
                                    @RequestParam(required = true) Integer status,
                                    @RequestParam(required = true) String appCode) {
        menuService.statusMenu(menuId, status, appCode);
        return RespMsg.ok();
    }

    @ApiOperation(value = "更新菜单树排序")
    @AccessLogger("更新系统菜单树排序")
    @PostMapping(value = "sort")
    public RespMsg<Void> updateMenuSort(@RequestBody List<SysMenu> menuList) {
        List<SysMenu> list = new ArrayList<>();
        menuList.stream().forEach(item -> {
            SysMenu sysMenu = new SysMenu();
            sysMenu.setId(item.getId());
            sysMenu.setPid(item.getPid());
            sysMenu.setSort(item.getSort());
            list.add(sysMenu);
        });
        menuService.updateMenuSort(list);
        return RespMsg.ok();
    }

    @ApiOperation(value = "展示菜单列表")
    @GetMapping("all")
    @ApiImplicitParam(name = "appCode", value = "系统编码", dataType = "String", required = true)
    public RespMsg<List<MenuDto>> findAll(@RequestParam(required = true) String appCode) {
        List<SysMenu> list = menuService.list(Condition.<SysMenu>create().eq(SysMenu.APPCODE, appCode).orderByAsc(SysMenu.SORT, SysMenu.ID));

        List<MenuDto> result = new ArrayList<>();
        list.stream().forEach(obj -> {
            MenuDto menuDto = new MenuDto(obj);
            result.add(menuDto);
        });
        return RespMsg.ok(MenuDto.makeTree(result));
    }

    @ApiOperation(value = "查询当前角色的菜单列表")
    @GetMapping("getRoleMenu")
    @ApiImplicitParam(name = "roleId", value = "角色ID", dataType = "Integer", required = true)
    public RespMsg<List<MenuDto>> getRoleMenu(@RequestParam(required = true) Integer roleId) {
        return RespMsg.ok(menuService.getRoleMenu(roleId));
    }

    @ApiOperation(value = "设置当前角色的菜单列表状态")
    @AccessLogger("设置当前角色的系统菜单列表状态")
    @PostMapping(value = "statusRoleMenu")
    public RespMsg<Void> statusRoleMenu(@RequestBody MenuListDto menuListDto) {
        menuService.statusRoleMenu(menuListDto);
        return RespMsg.ok();
    }
}
