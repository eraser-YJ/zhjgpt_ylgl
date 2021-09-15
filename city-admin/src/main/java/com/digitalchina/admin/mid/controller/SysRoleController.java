package com.digitalchina.admin.mid.controller;

import java.util.List;

import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.service.SysRoleAppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.log.AccessLogger;
import com.digitalchina.common.utils.PinyinUtils;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.service.SysDeptService;
import com.digitalchina.modules.service.SysRoleService;
import com.digitalchina.modules.service.SysRoleUserService;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 角色管理
 * @author: cwc
 * @date: 2019/8/29 10:47
 **/
@RestController
@RequestMapping("/sysRole")
//@Authorize
@Api(tags = "系统权限管理-角色管理")
public class SysRoleController {
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleAppService sysRoleAppService;

    @PostMapping("saveorupdate")
    @ApiOperation(value = "创建或者更新角色")
    @CacheEvict(value = "DefaultSysRoles", allEntries = true)
    @AccessLogger("创建或者更新系统角色,角色code：#{#role.code} 角色名称：#{#role.name}")
    public RespMsg<Void> saveOrUpdate(@RequestBody SysRole role) {
        String code = PinyinUtils.toPinyinString(role.getName(), PinyinUtils.LETTER_FIRST);// 汉语拼音首字母的合并
        role.setCode(getUniqueCode(code));// 获取唯一code
        roleService.saveOrUpdate(role);
        return RespMsg.ok();
    }

    private String getUniqueCode(String code) {
        if (roleService.checkExist(null, code) > 0) {
            // 存在就递归
            return getUniqueCode(code + "I");
        }
        return code;
    }

    @PostMapping("saveorupdate/event")
    @ApiOperation(value = "创建或者更新事件系统角色")
    @CacheEvict(value = "DefaultSysRoles", allEntries = true)
    @AccessLogger("创建或者更新事件系统系统角色,角色code：#{#role.code} 角色名称：#{#role.name}")
    public RespMsg<Void> saveOrUpdateEventRole(@RequestBody SysRole role) {
        roleService.saveOrUpdate(role);

        if (!sysRoleAppService.isAppRoleExist(role.getId(), SysCodeEnum.CITYEVENTNEW.getCode()))
            sysRoleAppService.saveAppRole(role.getId(), SysCodeEnum.CITYEVENTNEW.getCode());

        return RespMsg.ok();
    }

    @Deprecated
    @GetMapping("check")
    @ApiOperation(value = "检查角色编码是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "角色编码", dataType = "String", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)})
    public RespMsg<Boolean> checkExist(@RequestParam(required = true) String code,
                                       @RequestParam(required = true) Integer roleId) {
        if (roleService.checkExist(roleId, code) > 0) {
            return RespMsg.ok(Boolean.TRUE);
        } else {
            return RespMsg.ok(Boolean.FALSE);
        }
    }

    @GetMapping("find")
    @ApiOperation(value = "查找单个角色信息")
    @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)
    public RespMsg<SysRole> findRole(@RequestParam(required = true) Integer roleId) {
        return RespMsg.ok(roleService.getById(roleId));
    }

    @CacheEvict(value = "DefaultSysRoles", allEntries = true)
    @PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "删除角色")
    @AccessLogger("删除系统角色,角色Id:#{#roleId}")
    @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)
    public RespMsg<Void> delRole(@RequestParam(required = true) Integer roleId) {
        roleService.removeById(roleId);
        return RespMsg.ok();
    }

    @CacheEvict(value = "DefaultSysRoles", allEntries = true)
    @PostMapping(value = "deletes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "批量删除角色")
    @AccessLogger("批量删除系统角色,角色Id:#{#ids}")
    @ApiImplicitParam(name = "ids", value = "用户主键ID数组", dataType = "Integer[]", required = true)
    public RespMsg<Void> delRoles(Integer[] ids) {
        roleService.removeByIds(CollUtil.newArrayList(ids));
        return RespMsg.ok();
    }

    @GetMapping("query")
    @ApiOperation(value = "分页角色查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "roleName", value = "角色名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<SysRole>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                         @RequestParam(defaultValue = "1", required = true) Integer current,
                                         @RequestParam(required = false) String roleName) {
        IPage<SysRole> page = new Page<>(current, size);
        return RespMsg.ok(roleService.page(page, Condition.<SysRole>create()
                .like(StringUtils.isNotBlank(roleName), SysRole.NAME, roleName).orderByAsc(SysRole.ID)));
    }

    @GetMapping("all")
    @ApiOperation(value = "查询所有角色")
    public RespMsg<List<SysRole>> findAll() {
        return RespMsg.ok(roleService.list(Condition.<SysRole>create().orderByAsc(SysRole.ID)));
    }

    @GetMapping("alluser")
    @ApiOperation(value = "查询角色下所有用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "rid", value = "角色id", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "uname", value = "用户名称", dataType = "String", required = true)})
    public RespMsg<Page<SysUser>> findAllUser(@RequestParam(defaultValue = "10", required = true) Integer size,
                                              @RequestParam(defaultValue = "1", required = true) Integer current, Integer rid, String uname) {
        Page<SysUser> page = new Page<SysUser>(current, size);
        List<SysUser> users = sysRoleUserService.getAllByRole(page, rid, uname);
        // 补充部门名称
        users.stream().forEach(u -> {
            SysDept dept = sysDeptService.getById(u.getDpid());
            u.setBdnm(null == dept ? "" : dept.getBdnm());
        });
        page.setRecords(users);
        return RespMsg.ok(page);
    }

    @PostMapping("user/remove")
    @ApiOperation(value = "移除角色关联的用户")
    @ApiImplicitParams({@ApiImplicitParam(name = "rid", value = "角色id", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "uid", value = "用户id", dataType = "Integer", required = true)})
    public RespMsg<Void> removeUser(Integer rid, Integer uid) {
        sysRoleUserService.remove(Condition.<SysRoleUser>create().eq(SysRoleUser.UID, uid).eq(SysRoleUser.RID, rid));
        return RespMsg.ok();
    }

    @PostMapping("user/bind")
    @ApiOperation(value = "角色关联用户(批量")
    @ApiImplicitParams({@ApiImplicitParam(name = "rid", value = "角色id", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "uids", value = "用户id", dataType = "Integer[]", required = true)})
    public RespMsg<Void> bindUser(Integer rid, Integer[] uids) {
        if (null != uids && uids.length > 0) {
            for (Integer u : uids) {
                // 多数据源这里用不了批量保存
                sysRoleUserService.save(new SysRoleUser(u, rid));
            }
        }
        return RespMsg.ok();
    }

    @PostMapping(value = "defaultRole", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "是否默认角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dftype", value = "0：非默认 1：默认", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色Id", dataType = "Integer", required = true)})
    public RespMsg<Void> defaultRole(@RequestParam(required = true) Integer dftype,
                                     @RequestParam(required = true) Integer roleId) {
        roleService.defaultRole(dftype, roleId);
        return RespMsg.ok();
    }
}
