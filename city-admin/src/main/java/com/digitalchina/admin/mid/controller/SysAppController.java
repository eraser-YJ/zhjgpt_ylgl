package com.digitalchina.admin.mid.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.entity.SysApp;
import com.digitalchina.modules.service.SysAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 菜单管理
 * @author: cwc
 * @date: 2019/8/29 10:47
 **/
@RestController
@RequestMapping("/sysApp")
//@Authorize
@Api(tags = "系统权限管理-系统管理")
public class SysAppController {
    @Autowired
    private SysAppService appService;

    @PostMapping(value = "saveorupdate")
    @ApiOperation(value = "创建或者更新系统")
    public RespMsg<Void> saveOrUpdate(@RequestBody SysApp app) {
        appService.saveOrUpdate(app);
        return RespMsg.ok();
    }

    @GetMapping("check")
    @ApiOperation(value = "检查系统编码是否重复")
    @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "系统编码", dataType = "String", required = true),
            @ApiImplicitParam(name = "appId", value = "系统Id", dataType = "Integer", required = true)})
    public RespMsg<Boolean> checkExist(@RequestParam String code, @RequestParam Integer appId) {
        if (appService.checkExist(appId, code) > 0) {
            return RespMsg.ok(Boolean.TRUE);
        } else {
            return RespMsg.ok(Boolean.FALSE);
        }
    }

    @GetMapping("find")
    @ApiOperation(value = "查找单个系统信息")
    @ApiImplicitParam(name = "appId", value = "系统Id", dataType = "Integer", required = true)
    public RespMsg<SysApp> findApp(@RequestParam Integer appId) {
        return RespMsg.ok(appService.getById(appId));
    }

    @PostMapping(value = "delete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "删除系统")
    @ApiImplicitParam(name = "appId", value = "系统Id", dataType = "Integer", required = true)
    public RespMsg<Void> delApp(@RequestParam Integer appId) {
        appService.removeById(appId);
        return RespMsg.ok();
    }

    @PostMapping(value = "deletes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "批量删除系统")
    @ApiImplicitParam(name = "ids", value = "系统主键ID数组", dataType = "Integer[]", required = true)
    public RespMsg<Void> delRoles(Integer[] ids) {
        appService.removeByIds(CollUtil.newArrayList(ids));
        return RespMsg.ok();
    }

    @GetMapping("query")
    @ApiOperation(value = "分页系统查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "appName", value = "系统名称", dataType = "String"),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true)})
    public RespMsg<IPage<SysApp>> query(@RequestParam(defaultValue = "10") Integer size,
                                        @RequestParam(defaultValue = "1") Integer current,
                                        @RequestParam(required = false) String appName) {
        IPage<SysApp> page = new Page<>(current, size);
        return RespMsg.ok(appService.page(page, Condition.<SysApp>create()
                .like(StringUtils.isNotBlank(appName), SysApp.NAME, appName).orderByAsc(SysApp.ID)));
    }

    @GetMapping("queryAll")
    @ApiOperation(value = "查询所有系统信息列表，用于字典")
    public RespMsg<List<SysApp>> queryAll() {
        return RespMsg.ok(appService.list(null));
    }


}
