package com.digitalchina.admin.mid.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.admin.config.security.AdminSecurityUtil;
import com.digitalchina.admin.config.security.AmUserDetail;
import com.digitalchina.admin.config.security.TokenProvider;
import com.digitalchina.admin.mid.entity.AmSysRole;
import com.digitalchina.admin.mid.entity.AmSysUser;
import com.digitalchina.admin.mid.service.AmSysRoleService;
import com.digitalchina.admin.mid.service.AmSysUserService;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.enums.UserStatusEnum;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.utils.PwdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 登录接口
 * 
 * @author warrior
 *
 * @since 2019年8月27日
 */
@RestController
@Api(tags = "登录接口")
public class LoginController {

	@Autowired
	private TokenProvider tp;

	@Autowired
	private AmSysUserService ss;

	@Autowired
	private AmSysRoleService rs;

	@ApiOperation(value = "用户登录")
	@GetMapping(value = "/login")
	@ApiImplicitParams({ @ApiImplicitParam(name = "login", value = "用户名", dataType = "String", required = true),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true) })
	public RespMsg<String> login(String login, String password) {
		AmSysUser user = ss.getOne(Condition.<AmSysUser>create().eq(AmSysUser.LOGIN, login));
		if (user == null) {
			return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
		}
		if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
			return RespMsg.error(SecurityConstant.ACCOUNT_FREEZE, "账号已被冻结！");
		}
		if (!PwdUtil.verify(password, user.getPassword())) {
			return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
		}
		List<AmSysRole> roles = rs.findRolesByUserId(user.getId());
		AmUserDetail detail = new AmUserDetail(user, roles);
		return RespMsg.ok(tp.createToken(detail));
	}

	@GetMapping("/token/validate")
	@ApiOperation("验证token是否有效")
	public RespMsg<Boolean> validateToken() {
		if (AdminSecurityUtil.currentUser() != null) {
			return RespMsg.ok(true);
		} else {
			return RespMsg.ok(false);
		}
	}

	@Authorize
	@GetMapping("/current/user")
	@ApiOperation("获取当前登录用户的信息")
	public RespMsg<AmUserDetail> currentUser() {
		return RespMsg.ok(AdminSecurityUtil.currentUser());
	}


	@ApiOperation(value = "退出登录")
	@GetMapping(value = "/logout")
	public RespMsg logout(HttpServletRequest request) throws UnsupportedEncodingException {
		tp.clearToken(request.getHeader(SecurityConstant.AUTH_TOKEN));
		return RespMsg.ok();
	}

}
