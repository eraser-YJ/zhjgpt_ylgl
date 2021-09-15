package com.digitalchina.admin.mid.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.log.AccessLogger;
import com.digitalchina.admin.mid.dto.AmMenuDto;
import com.digitalchina.admin.mid.dto.RoleMenuDto;
import com.digitalchina.admin.mid.entity.AmSysRole;
import com.digitalchina.admin.mid.service.AmSysMenuService;
import com.digitalchina.admin.mid.service.AmSysRoleMenuService;
import com.digitalchina.admin.mid.service.AmSysRoleService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/amSysRole")
@Authorize
@Api(tags = "后台管理-系统角色")
public class AmSysRoleController {

    @Autowired
    private AmSysRoleService amSysRoleService;

    @Autowired
    private AmSysMenuService amSysMenuService;

    @Autowired
    private AmSysRoleMenuService amSysRoleMenuService;

    @PostMapping(value = "saveorupdate")
    @ApiOperation(value = "创建或者更新角色")
    @AccessLogger("创建或者更新后台管理角色:角色code：#{#role.code} 角色名称：#{#role.name}")
    public RespMsg<Void> saveorupdate(@RequestBody AmSysRole role) {
        amSysRoleService.saveOrUpdate(role);
        return RespMsg.ok();
    }

    @GetMapping(value = "check")
    @ApiOperation(value = "检查角色编码是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "系统编码", dataType = "String", required = true),
            @ApiImplicitParam(name = "appId", value = "系统Id", dataType = "Integer", required = true)})
    public RespMsg<Boolean> checkExist(@RequestParam(required = true) String code, @RequestParam(required = true) Integer appId) {
        if (amSysRoleService.checkExist(appId, code) > 0) {
            return RespMsg.ok(Boolean.TRUE);
        } else {
            return RespMsg.ok(Boolean.FALSE);
        }
    }

    @GetMapping("find")
    @ApiOperation(value = "查找单个角色信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)})
    public RespMsg<AmSysRole> findRole(@RequestParam(required = true) Integer roleId) {
        return RespMsg.ok(amSysRoleService.getById(roleId));
    }

    @PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "删除角色")
    @AccessLogger("删除后台管理角色Id:#{#roleId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)})
    public RespMsg<Void> delRole(@RequestParam(required = true) Integer roleId) {
        amSysRoleService.removeById(roleId);
        return RespMsg.ok();
    }

    @GetMapping("query")
    @ApiOperation(value = "分页角色查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = false)})
    public RespMsg<IPage<AmSysRole>> query(@RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam(defaultValue = "1") Integer current,
                                           @RequestParam String roleName) {
        IPage<AmSysRole> page = new Page<>(current, size);
        return RespMsg.ok(amSysRoleService.page(page, Condition.<AmSysRole>create()
                .like(StringUtils.isNotBlank(roleName), AmSysRole.NAME, roleName).orderByAsc(AmSysRole.ID)));
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有角色")
    public RespMsg<List<AmSysRole>> findAll() {
        return RespMsg.ok(amSysRoleService.list(Condition.<AmSysRole>create().orderByAsc(AmSysRole.ID)));
    }

    @GetMapping("menu")
    @ApiOperation(value = "查询角色的授权菜单列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "int", required = true)})
    public RespMsg<List<AmMenuDto>> findRoleMenu(Integer roleId) {
        return RespMsg.ok(amSysMenuService.findRoleMenu(roleId));
    }

    @PostMapping(value = "authmenu", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "角色菜单授权")
    @AccessLogger("后台管理角色菜单授权")
    public RespMsg<Void> authMenu(@RequestBody RoleMenuDto roleMenuDto) {
        amSysRoleMenuService.authMenu(roleMenuDto);
        return RespMsg.ok();
    }
}
