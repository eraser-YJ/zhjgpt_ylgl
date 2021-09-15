package com.digitalchina.admin.mid.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.digitalchina.modules.service.SysRoleUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
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
import com.digitalchina.admin.remoteservice.CityEventService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.SysDept;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.service.SysDeptService;
import com.digitalchina.modules.service.SysRoleAppService;
import com.digitalchina.modules.service.SysUserService;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @description: 用户管理
 * @author: cwc
 * @date: 2019/8/29 10:46
 **/
@RestController
@RequestMapping("/sysUser")
//@Authorize
@Api(tags = "系统权限管理-用户管理")
public class SysUserController {
    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleAppService sysRoleAppService;
    @Autowired
    private CityEventService cityEventService;
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @GetMapping("find")
    @ApiOperation(value = "查看")
    @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true)
    public RespMsg<SysUser> findInfo(@RequestParam(required = true) Integer userId) {
        return RespMsg.ok(userService.findInfo(userId));
    }

    @GetMapping("findRole")
    @ApiOperation(value = "查看用户拥有的角色")
    @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true)
    public RespMsg<List<SysRole>> findRole(@RequestParam(required = true) Integer userId) {
        return RespMsg.ok(userService.findRole(userId));
    }

    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    @PostMapping(value = "empower", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "授权")
    @AccessLogger("授予系统用户id为：#{#userId}角色权限id为:#{#roleIds}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "角色主键ID数组", dataType = "Integer[]", required = true),
            @ApiImplicitParam(name = "userId", value = "用户Id", dataType = "Integer", required = true)})
    public RespMsg<Void> empowerRole(@RequestParam(required = true) Integer[] roleIds,
                                     @RequestParam(required = true) Integer userId) {
        if (null == roleIds || roleIds.length < 1) {
            return RespMsg.error("至少分配一个角色");
        }
        userService.empowerRole(roleIds, userId);
        // 判断是否角色属于事件系统 同步用户信息 加事物，传播特性
        int cnt = sysRoleAppService.selectSysCodeCnt(roleIds, SysCodeEnum.CITYEVENTNEW.getCode());
        if (cnt > 0) {
            SysUser sysUser = userService.loadUserRoleById(userId);
            try {
                cityEventService.syncUserInfo(sysUser);
            } catch (Exception e) {
                throw new ServiceException("同步用户信息至事件系统异常！" + e.getMessage());
            }
        }
        return RespMsg.ok();
    }

    @GetMapping("query")
    @ApiOperation(value = "分页模糊查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String", required = false),
            @ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
            @ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true),
            @ApiImplicitParam(name = "rid", value = "角色id", dataType = "int", required = false)})
    public RespMsg<IPage<SysUser>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
                                         @RequestParam(defaultValue = "1", required = true) Integer current,
                                         @RequestParam(required = false) String userName
                                         /*@RequestParam(required = false) Integer rid*/) {

/*        List<SysUser> userList = sysRoleUserService.getAllByRole(rid, userName);
        List<Integer> uidList = userList.stream().map(item -> item.getId()).collect(Collectors.toList());*/

        IPage<SysUser> page = new Page<>(current, size);
        IPage<SysUser> result = userService.page(page, Condition.<SysUser>create()
                .like(StringUtils.isNotBlank(userName), SysUser.NAME, userName)
/*                .notIn(StringUtils.isNotBlank(userName), SysUser.ID, uidList)*/
                .orderByAsc(SysUser.ID));

        // 有人员信息,组装人员信息的部门树结构
//        if (CollUtil.isNotEmpty(result.getRecords())) {
//            Map<Integer, String> dept = sysDeptService.list(null).stream()
//                    .collect(Collectors.toMap(SysDept::getDpid, SysDept::getBdnm));
//
//            result.getRecords().forEach(r -> {
//                if (null != r.getBdpntids() && r.getBdpntids().length > 0) {
//                    StringBuilder sb = new StringBuilder();
//                    for (Integer bdid : r.getBdpntids()) {
//                        sb.append("/" + dept.get(bdid));
//                    }
//                    r.setBdnm(sb.toString().replaceFirst("/", ""));// 移除第一个多余的分隔
//                }
//            });
//        }
        return RespMsg.ok(page);
    }

    @GetMapping("info")
    @ApiOperation(value = "详情")
    @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer", required = false)
    public RespMsg<SysUser> info(Integer id) {
        return RespMsg.ok(userService.getById(id));
    }

//	@PostMapping("edit")
//	@ApiOperation(value = "编辑",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户id", dataType = "Integer", required = false),
//			@ApiImplicitParam(name = "dpid", value = "部门id", dataType = "Integer", required = false),
//			@ApiImplicitParam(name = "bdpntids", value = "上级部门集合", dataType = "Arrary", required = false),
//			@ApiImplicitParam(name = "tel", value = "号码", dataType = "String", required = true),
//			@ApiImplicitParam(name = "mark", value = "备注", dataType = "String", required = true) })
//	public RespMsg<Void> query(Integer id, String tel, String mark, Integer dpid, Integer[] bdpntids) {

    @PostMapping("edit")
    @ApiOperation(value = "编辑")
    public RespMsg<Void> edit(@RequestBody SysUser user) {
        SysUser userent = userService.getById(user.getId());

        SysUser updateent = new SysUser();
        updateent.setId(user.getId());
        updateent.setTel(user.getTel());
        updateent.setMark(user.getMark());
        // 部门信息
        if (null != user.getBdpntids() && user.getBdpntids().length > 0) {
            Integer dpid = user.getBdpntids()[user.getBdpntids().length - 1];
            updateent.setDpid(dpid);
            updateent.setBdpntids(user.getBdpntids());
            // 更改部门信息后同步事件系统
            cityEventService.empowerDept2(userent.getLogin(), dpid);
        }
        userService.updateById(updateent);
        return RespMsg.ok();
    }
}
