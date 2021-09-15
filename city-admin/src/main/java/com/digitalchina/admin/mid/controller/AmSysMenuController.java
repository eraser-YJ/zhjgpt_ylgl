package com.digitalchina.admin.mid.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.config.security.AdminSecurityUtil;
import com.digitalchina.admin.log.AccessLogger;
import com.digitalchina.admin.mid.dto.AmMenuDto;
import com.digitalchina.admin.mid.entity.AmSysMenu;
import com.digitalchina.admin.mid.service.AmSysMenuService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/amSysMenu")
@Authorize
@Api(tags = "后台管理-系统菜单")
public class AmSysMenuController {

	@Autowired
	private AmSysMenuService amSysMenuService;

	@ApiOperation(value = "创建或者更新菜单")
	@AccessLogger("创建或者更新菜单名称为：#{#menu.name} 的后台管理菜单")
	@PostMapping(value = "saveorupdate")
	public RespMsg<Void> saveorupdate(@RequestBody AmSysMenu menu) {
		amSysMenuService.saveOrUpdate(menu);
		return RespMsg.ok();
	}

	@ApiOperation(value = "删除菜单")
	@AccessLogger("删除后台管理菜单ID:#{#menuId}")
	@PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiImplicitParam(name = "menuId", value = "菜单ID", dataType = "Integer", required = true)
	public RespMsg<Void> deleteMenu(Integer menuId) {
		amSysMenuService
				.remove((Condition.<AmSysMenu>create().eq(AmSysMenu.ID, menuId).or().eq(AmSysMenu.PID, menuId)));
		return RespMsg.ok();
	}

	@ApiOperation(value = "修改菜单状态")
	@AccessLogger("修改后台管理菜单ID:#{#dto.id}的状态为:#{#dto.status}")
	@PostMapping(value = "status", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<Void> statusMenu(@RequestBody AmSysMenu dto) {
		AmSysMenu amSysMenu = new AmSysMenu();
		amSysMenu.setId(dto.getId());
		amSysMenu.setStatus(dto.getStatus());
		amSysMenuService.updateById(amSysMenu);
		return RespMsg.ok();
	}

	@ApiOperation(value = "更新整个菜单树")
	@AccessLogger("更新后台管理整个菜单树")
	@PostMapping(value = "tree/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public RespMsg<Void> updateMenuTree(@RequestBody List<AmSysMenu> menuList) {
		for (AmSysMenu m : menuList) {
			amSysMenuService.save(m);
		}
		return RespMsg.ok();
	}

	@ApiOperation(value = "所有菜单的列表")
	@GetMapping("all")
	public RespMsg<List<AmMenuDto>> findAll() {
		List<AmSysMenu> list = amSysMenuService.list(Condition.<AmSysMenu>create().orderByAsc(AmSysMenu.SORT));
		return RespMsg
				.ok(AmMenuDto.makeTree(list.stream().map(item -> new AmMenuDto(item)).collect(Collectors.toList())));
	}

	@ApiOperation(value = "查询当前用户菜单")
	@GetMapping("user")
	public RespMsg<List<AmMenuDto>> findUserMenu() {
		return RespMsg.ok(amSysMenuService.findUserMenu(AdminSecurityUtil.currentUserId()));
	}
}
