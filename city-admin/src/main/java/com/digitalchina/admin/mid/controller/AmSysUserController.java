package com.digitalchina.admin.mid.controller;


import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.log.AccessLogger;
import com.digitalchina.admin.mid.entity.AmSysUser;
import com.digitalchina.admin.mid.service.AmSysUserService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.utils.PwdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
@RestController
@RequestMapping("/amSysUser")
@Authorize
@Api(tags = "后台管理-系统用户")
public class AmSysUserController {

    @Autowired
    private AmSysUserService amSysUserService;

    @PostMapping(value = "create")
    @ApiOperation(value = "创建或者更新用户")
    @AccessLogger("创建或者更新登录名为：#{#user.login}的后台管理用户")
    public RespMsg<Void> saveorupdate(@RequestBody AmSysUser user) {
        user.setPassword(PwdUtil.generate(user.getPassword()));
        amSysUserService.saveOrUpdate(user);
        return RespMsg.ok();
    }

    @GetMapping("check")
    @ApiOperation(value = "检查登录名是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "login", value = "登录名", dataType = "String", required = true),
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true)})
    public RespMsg<Boolean> checkExist(@RequestParam(required = true) String login, @RequestParam(required = true) Integer userId) {
        if (amSysUserService.checkExist(userId, login) > 0) {
            return RespMsg.ok(Boolean.TRUE);
        } else {
            return RespMsg.ok(Boolean.FALSE);
        }
    }

    @PostMapping(value = "reset", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "重置密码")
    @AccessLogger("后台管理用户ID：#{#userId}重置了密码")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Integer", required = true)
    public RespMsg<Void> resetPasswd(@RequestParam(required = true) Integer userId) {
        AmSysUser amSysUser = new AmSysUser();
        amSysUser.setId(userId);
        amSysUser.setPassword(StringUtils.EMPTY);
        amSysUserService.updateById(amSysUser);
        return RespMsg.ok();
    }

    @GetMapping("query")
    @ApiOperation(value = "分页用户查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = false),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = false)})
    public RespMsg<IPage<AmSysUser>> query(@RequestParam(defaultValue = "10") Integer size,
                                           @RequestParam(defaultValue = "1") Integer current,
                                           @RequestParam String userName) {
        IPage<AmSysUser> page = new Page<>(current, size);
        return RespMsg.ok(amSysUserService.page(page, Condition.<AmSysUser>create()
                .like(StringUtils.isNotBlank(userName), AmSysUser.NAME, userName).orderByAsc(AmSysUser.ID)));
    }

    @GetMapping("find")
    @ApiOperation(value = "单个用户查询")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Integer", required = true)
    public RespMsg<AmSysUser> findUser(@RequestParam(required = true) Integer userId) {
        return RespMsg.ok(amSysUserService.getById(userId));
    }

    @PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "删除用户")
    @AccessLogger("删除后台管理用户ID：#{#userId}")
    @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Integer", required = true)
    public RespMsg<Void> deleteUser(@RequestParam(required = true) Integer userId) {
        amSysUserService.removeById(userId);
        return RespMsg.ok();
    }

    @PostMapping(value = "status", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "启用禁用")
    @AccessLogger("启用或禁用后台管理用户ID：#{#user.id} 状态为：#{#user.status}")
    public RespMsg<Void> changeStatus(@RequestBody AmSysUser user) {
        AmSysUser amSysUser = new AmSysUser();
        amSysUser.setId(user.getId());
        amSysUser.setStatus(user.getStatus());
        amSysUserService.updateById(amSysUser);
        return RespMsg.ok();
    }
}
