package com.digitalchina.gateway.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.gateway.config.properties.SsoProperties;
import com.digitalchina.gateway.dto.PasswordDto;
import com.digitalchina.gateway.dto.UserDto;
import com.digitalchina.modules.aop.LogAdvice;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.constant.enums.UserStatusEnum;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.log.SysLogger;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.TokenProvider;
import com.digitalchina.modules.security.UserDetail;
import com.digitalchina.modules.security.UserTokenHolder;
import com.digitalchina.modules.service.SysAppService;
import com.digitalchina.modules.service.SysRoleService;
import com.digitalchina.modules.service.SysUserService;
import com.digitalchina.modules.utils.PwdUtil;
import com.digitalchina.modules.utils.RSAUtils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "基础接口")
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LogAdvice.class);

	@Autowired
	private TokenProvider tp;

	@Autowired
	private UserTokenHolder uth;

	@Autowired
	private SysUserService ss;

	@Autowired
	private SysRoleService rs;

	@Autowired
	private SsoProperties sso;

	@Autowired
	private SysAppService sas;

	@SysLogger("登录系统")
	@ApiOperation(value = "用户登录")
	@GetMapping(value = "/login")
	@ApiImplicitParams({ @ApiImplicitParam(name = "login", value = "用户名", dataType = "String", required = true),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true) })
	public RespMsg<String> login(String login, String password) {
		SysUser user = ss.getOne(Condition.<SysUser>create().eq(SysUser.LOGIN, login));
		if (user == null) {
			return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
		}
		if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
			return RespMsg.error(SecurityConstant.ACCOUNT_FREEZE, "账号已被冻结！");
		}
		if (!PwdUtil.verify(password, user.getPassword())) {
			return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
		}
		List<SysRole> roles = rs.findRolesByUserId(user.getId());
		List<String> apps = sas.findAppCodeByUserId(user.getId());
		UserDetail detail = new UserDetail(user, roles, apps, false);
		// 用户登录需要记录日志，这里要把当前用户设进去，否则切面拿不到信息
		SecurityUtil.setCurrentUser(detail);

		// 杨凌系统要返回用户信息，为保持和长春的兼容性
		RespMsg<String> result = RespMsg.ok(tp.createToken(detail));
		result.setExtend(detail);
		return result;
	}

	@GetMapping("/token/validate")
	@ApiOperation("验证token是否有效")
	public RespMsg<Boolean> validateToken() {
		if (SecurityUtil.currentUser() != null) {
			return RespMsg.ok(true);
		} else {
			return RespMsg.ok(false);
		}
	}

	@Authorize
	@GetMapping("/current/user")
	@ApiOperation("获取当前登录用户的信息")
	public RespMsg<UserDetail> currentUser() {
		return RespMsg.ok(SecurityUtil.currentUser());
	}

	@ApiOperation(value = "单点登录-统一门户路径")
	@GetMapping(value = "sso/url")
	@ApiImplicitParam(name = "app", value = "系统标识", dataType = "String", required = true)
	public RespMsg<String> ssologin(@RequestParam(required = true) String app) {
		return RespMsg.ok(sso.getLoginUrl());
	}

	@ApiOperation(value = "单点登录")
	@GetMapping(value = "sso/login")
	@SysLogger("登录系统")
	@ApiImplicitParams({ @ApiImplicitParam(name = "app", value = "系统标识", dataType = "String", required = true),
			@ApiImplicitParam(name = "token", value = "统一认证传过来的token", dataType = "String", required = true) })
	public RespMsg<String> ssologin(@RequestParam(required = true) String app, String token) {
		return unifiedLogin(app, token, null);
	}

	/**
	 * 统一登录认证
	 * 
	 * @param app
	 * @param token
	 * @param login
	 * @return
	 */
	private RespMsg<String> unifiedLogin(String app, String token, String login) {
		if (StrUtil.isBlank(token)) {
			return RespMsg.error(SecurityConstant.NEED_RELOGIN, "需要重新登录", sso.getLoginUrl());
		}
		String content = HttpUtil.get(sso.createInfoUrl(token));
		if (log.isDebugEnabled()) {
			log.debug("返回的用户信息为：{}", content);
		}
		JSONObject json = JSONObject.parseObject(content);
		Validator.validateEqual(json.getInteger("code"), 200, "返回的用户信息不正确！json=" + content);
		String ssoid = json.getString("data");
		SysUser user = ss.getOne(Condition.<SysUser>create().eq(SysUser.SSOID, ssoid).eq(StrUtil.isNotBlank(login),
				SysUser.LOGIN, login));
		Validator.validateNotNull(user, "没有找到ssoid={}的用户", ssoid);
		UserDetail detail = new UserDetail(user, null, null, true);
		SecurityUtil.setCurrentUser(detail);
		if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
			return RespMsg.error(SecurityConstant.ACCOUNT_FREEZE, "账号已被冻结！");
		}
		List<SysRole> roles = rs.findRolesByUserId(user.getId());
		List<String> apps = sas.findAppCodeByUserId(user.getId());
		detail = new UserDetail(user, roles, apps, true);
		// 用户登录需要记录日志，这里要把当前用户设进去，否则切面拿不到信息
		SecurityUtil.setCurrentUser(detail);
		if (!apps.contains(app)) {
			return RespMsg.error(SecurityConstant.UNAUTHORIZED, "用户没有权限登录系统，请联系管理员！", sso.getPortalUrl());
		}
		String authorizeToken = tp.createToken(detail);
		if (SysCodeEnum.CITYAPP.getCode().equals(app)) {
			// 只能有一台设备登录
			resetAuthorize(app, user.getId().toString(), authorizeToken);
		}
		// 用户登录需要记录日志，这里要把当前用户设进去，否则切面拿不到信息
		SecurityUtil.setCurrentUser(detail);
		return RespMsg.ok(authorizeToken);
	}

	/**
	 * 一个系统只允许一台设备登录时,重置授权
	 * 
	 * @param app
	 *            系统
	 * @param userId
	 *            用户id
	 */
	private synchronized void resetAuthorize(String app, String userId, String newToken) {
		String UserToken = uth.getUserToken(app, userId);
		if (StrUtil.isNotEmpty(UserToken)) {
			// 清除上次登录授权
			tp.clearToken(UserToken);
		}
		uth.saveUserToken(app, userId, newToken);
	}

	@ApiOperation(value = "退出登录", notes = "如果data有返回值，则前端自动根据返回值跳转，否则跳转到系统的登录页")
	@GetMapping(value = "/logout")
	public RespMsg<String> so(HttpServletRequest request) throws UnsupportedEncodingException {
		String loginOut = null;
		UserDetail currentUser = SecurityUtil.currentUser();
		if (currentUser != null && currentUser.isLoginFromPortal()) {
			loginOut = sso.createLoginOutUrl();
		}
		tp.clearToken(request.getHeader(SecurityConstant.AUTH_TOKEN));
		return RespMsg.ok(loginOut);
	}

	@ApiOperation(value = "领导决策APP登录")
	@PostMapping(value = "/login/cityapp", consumes = MediaType.APPLICATION_JSON_VALUE)
	@SysLogger("登录系统")
	public RespMsg<String> appLogin(@RequestBody @Validated UserDto userDto) throws Exception {
		String login = RSAUtils.decryptByPrivateKey(userDto.getLogin());
		String password = RSAUtils.decryptByPrivateKey(userDto.getPassword());
		return unifiedLogin(SysCodeEnum.CITYAPP.getCode(), authonLogin(login, password), login);
	}

	@ApiOperation(value = "领导决策APP退出登录")
	@GetMapping(value = "/logout/cityapp")
	public RespMsg<String> appLogout(HttpServletRequest request) {
		tp.clearToken(request.getHeader(SecurityConstant.AUTH_TOKEN));
		return RespMsg.ok();
	}

	/**
	 * 获取授权
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	private String authonLogin(String login, String password) {
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("userName", login);
		paramMap.put("password", Base64.encode(password));
		String content = HttpUtil.post(sso.getAuthLoginUrl(), paramMap);
		if (log.isDebugEnabled()) {
			log.debug("返回的登录信息为：{}", content);
		}
		JSONObject json = JSONObject.parseObject(content);
		// Validator.validateEqual(json.getInteger("code"), 200,
		// "返回的登录信息不正确！json=" + content);
		if (json.getInteger("code") != 200) {
			throw new ServiceException(999, json.getString("message"));
		}
		return json.getString("data");
	}

	@Authorize
	@ApiOperation(value = "领导决策APP用户修改密码")
	@PostMapping(value = "/upPwd/cityapp", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RespMsg<String> upPwd(@RequestBody @Validated PasswordDto passwordDto) throws Exception {
		String oldPassword = passwordDto.getOldPassword();
		String newPassword = passwordDto.getNewPassword();

		if (oldPassword.equals(newPassword)) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "新旧密码不能相同！");
		}
		oldPassword = RSAUtils.decryptByPrivateKey(oldPassword);
		newPassword = RSAUtils.decryptByPrivateKey(newPassword);

		UserDetail detail = SecurityUtil.currentUser();
		if (detail == null) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "登录已失效,请重新登录！");
		}
		// 需要拿到统一认证的token
		String jwt = null;
		try {
			jwt = authonLogin(detail.getLogin(), oldPassword);
		} catch (Exception e) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "旧密码输入错误！");
		}
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("oldPassword", Base64.encode(oldPassword));
		paramMap.put("newPassword", Base64.encode(newPassword));
		if (log.isDebugEnabled()) {
			log.debug("准备修改的密码是：{}", Base64.encode(newPassword));
		}
		String result = HttpRequest.post(sso.getUpPwdUrl()).header("jwt-token", jwt)// 头信息，多个头信息多次调用此方法即可
				.form(paramMap)// 表单内容
				.execute().body();
		if (log.isDebugEnabled()) {
			log.debug("返回的修改密码的结果为：{}", result);
		}
		JSONObject json = JSONObject.parseObject(result);
		try {
			Validator.validateEqual(json.getInteger("code"), 200, "返回的修改密码的结果不正确！json=" + result);
		} catch (Exception e) {
			return RespMsg.error(HttpStatus.HTTP_BAD_REQUEST, "修改密码时异常：" + json.getString("message"));
		}
		return RespMsg.ok();
	}

}
