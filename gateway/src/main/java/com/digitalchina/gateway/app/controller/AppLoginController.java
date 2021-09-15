package com.digitalchina.gateway.app.controller;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.gateway.app.service.AppSysUserService;
import com.digitalchina.gateway.config.properties.SsoProperties;
import com.digitalchina.modules.aop.LogAdvice;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.constant.enums.UserStatusEnum;

import com.digitalchina.modules.entity.AppSysUser;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.log.SysLogger;
import com.digitalchina.modules.security.*;

import com.digitalchina.modules.service.SysRoleService;
import com.digitalchina.modules.service.SysUserService;
import com.digitalchina.modules.utils.PwdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Api(tags = "基础接口")
public class AppLoginController {

    private static final Logger log = LoggerFactory.getLogger(LogAdvice.class);

    @Autowired
    private TokenProvider tp;

    @Autowired
    private UserTokenHolder uth;

    @Autowired
    private AppSysUserService ass;

    @Autowired
    private SysRoleService rs;

    @Autowired
    private SsoProperties sso;


    @SysLogger("移动端登录系统")
    @ApiOperation(value = "用户登录")
    @GetMapping(value = "/appLogin")
    @ApiImplicitParams({ @ApiImplicitParam(name = "login", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true) })
    public RespMsg<Map> appLogin(String login, String password) {
        AppSysUser user = ass.getOne(Condition.<AppSysUser>create().eq(AppSysUser.USERACCNUM, login));
        if (user == null) {
            return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
        }
        if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
            return RespMsg.error(SecurityConstant.ACCOUNT_FREEZE, "账号已被冻结！");
        }
        if (!PwdUtil.verify(password, user.getUserPassword())) {
            return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
        }
        //List<SysRole> roles = rs.findRolesByUserId(user.getUserId());
        //List<String> apps = sas.findAppCodeByUserId(user.getUserId());
        List<SysRole> roles = new ArrayList<>();
        List<String> apps = new ArrayList<>();
        UserDetail detail = new UserDetail(user, roles, apps, false);
        //有移动端登录的企业用户,记录客户来源
        detail.setUserSource(UserSource.QYUSER);
        //实体类记录用户基本信息
        detail.setId(user.getUserId());
        detail.setName(user.getUserName());
        detail.setLogin(user.getUserAccnum());
        // 用户登录需要记录日志，这里要把当前用户设进去，否则切面拿不到信息
        SecurityUtil.setCurrentUser(detail);
        Map<String,Object> map = new HashMap<>();
        map.put("token",tp.createToken(detail));
        map.put("userName",user.getUserName());
        map.put("userAccnum",user.getUserAccnum());
        map.put("userPhone",user.getUserPhone());
        map.put("enterName",user.getEnterName());
        return RespMsg.ok(map);
    }
}
