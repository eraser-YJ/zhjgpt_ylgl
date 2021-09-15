package com.digitalchina.event.mid.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.DeptUser;
import com.digitalchina.event.mid.entity.LoginUser;
import com.digitalchina.event.mid.service.DeptUserService;
import com.digitalchina.event.mid.service.LoginUserService;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.TransConstant;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.util.ObjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 登陆过事件系统的用户 前端控制器
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@Slf4j
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/loginUser")
public class LoginUserController {

	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private DeptUserService deptUserService;

	@Authorize(SecurityConstant.FORBIDDEN)
	@PostMapping("syncUserInfo")
	@ApiOperation(value = "同步用户信息")
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
	public RespMsg<Void> syncUserInfo(@RequestBody SysUser sysUser) {
		LoginUser loginUser = new LoginUser(sysUser);
		LoginUser old = loginUserService.getById(sysUser.getId());
		if (loginUser.equals(old)) {
			return RespMsg.ok();
		}
		if (ObjectUtil.isEmpty(old)) {
			loginUserService.save(loginUser);
		} else {
			loginUserService.updateById(loginUser);
		}
		identityService.deleteUser(loginUser.getId().toString());
		User user = identityService.newUser(loginUser.getId().toString());
		user.setFirstName(sysUser.getLogin());
		user.setLastName(sysUser.getName());
		identityService.saveUser(user);
		return RespMsg.ok();
	}

	@Deprecated
	@CacheEvict(value = "curBedept", key = "#uid")
	@Authorize(SecurityConstant.FORBIDDEN)
	@GetMapping(value = "empower")
	@ApiOperation(value = "给用户指定部门(弃置)")
	@Transactional(value = TransConstant.EVENT_TRANSACTION_MANAGER)
	@ApiImplicitParams({ @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "Integer", required = true),
			@ApiImplicitParam(name = "did", value = "部门id", dataType = "Integer", required = true) })
	public RespMsg<Void> empowerDept(@RequestParam(required = true) Integer uid,
			@RequestParam(required = true) Integer did) {
		DeptUser deptUser = deptUserService.getOne(Condition.<DeptUser>lambda().eq(DeptUser::getUid, uid));
		if (ObjectUtil.isNotEmpty(deptUser)) {
			deptUserService.removeById(deptUser.getId());
			identityService.deleteMembership(deptUser.getUid().toString(), deptUser.getDid().toString());
		}
		deptUserService.save(DeptUser.builder().uid(uid).did(did).build());
		identityService.createMembership(uid.toString(), did.toString());
		return RespMsg.ok();
	}

	@GetMapping(value = "empower2")
	@ApiOperation(value = "给用户指定部门(管理系统同步专用)")
	@ApiImplicitParams({ @ApiImplicitParam(name = "login", value = "登陆用户", dataType = "String", required = true),
			@ApiImplicitParam(name = "did", value = "部门id", dataType = "Integer", required = true) })
	public RespMsg<Void> empowerDept2(@RequestParam(required = true) String login,
			@RequestParam(required = true) Integer did) {
		LoginUser user = loginUserService.getOne(Condition.<LoginUser>lambda().eq(LoginUser::getLogin, login));
		if (null == user) {
			log.info("管理系统同步用户信息》更新部门》{}不存在，跳过", login);
			return RespMsg.ok();
		}
		DeptUser deptUser = deptUserService.getOne(Condition.<DeptUser>lambda().eq(DeptUser::getUid, user.getId()));
		if (ObjectUtil.isNotEmpty(deptUser)) {
			deptUserService.removeById(deptUser.getId());
			identityService.deleteMembership(deptUser.getUid().toString(), deptUser.getDid().toString());
		}
		deptUserService.save(DeptUser.builder().uid(user.getId()).did(did).build());
		identityService.createMembership(user.getId().toString(), did.toString());
		return RespMsg.ok();
	}

	@Authorize
	@GetMapping(value = "query")
	@ApiOperation(value = "分页查询")
	@ApiImplicitParams({ @ApiImplicitParam(name = "login", value = "登录名", dataType = "String", required = false),
			@ApiImplicitParam(name = "name", value = "用户名称", dataType = "String", required = false),
			@ApiImplicitParam(name = "dept", value = "所属部门", dataType = "String", required = false),
			@ApiImplicitParam(name = "size", value = "每页条数，默认10条", dataType = "int", required = true),
			@ApiImplicitParam(name = "current", value = "第几页", dataType = "int", required = true) })
	public RespMsg<IPage<LoginUser>> query(@RequestParam(defaultValue = "10", required = true) Integer size,
			@RequestParam(defaultValue = "1", required = true) Integer current,
			@RequestParam(required = false) String login, @RequestParam(required = false) String name,
			@RequestParam(required = false) String dept) {
		IPage<LoginUser> page = new Page<>(current, size);
		return RespMsg.ok(loginUserService.query(page, login, name, dept));
	}
}
