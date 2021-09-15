package com.digitalchina.gateway.zjb.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.gateway.config.properties.SsoProperties;
import com.digitalchina.modules.aop.LogAdvice;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;
import com.digitalchina.modules.log.SysLogger;
import com.digitalchina.modules.security.*;
import com.digitalchina.modules.service.SysRoleService;
import com.digitalchina.modules.service.SysUserService;
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
public class AppLoginZJBController {

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

    @SysLogger("移动端登录系统")
    @ApiOperation(value = "住建部用户登录")
    @GetMapping(value = "/appZJBLogin")
    @ApiImplicitParams({@ApiImplicitParam(name = "login", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)})
    public RespMsg<Map> appLogin(String login, String password) {
        HashMap<String, Object> paramMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        //请求统一门户登录接口
        paramMap.put("userName", login);
        paramMap.put("password", Base64.encode(password));
        String content = HttpUtil.post("http://10.0.251.71:18080/auth/user/login", paramMap);
        JSONObject json = JSONObject.parseObject(content);
        System.out.println(json.getString("code"));
        if (!json.getString("code").equals("200")) {
            return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
        } else {
            SysUser user = ss.getOne(Condition.<SysUser>create().eq(SysUser.LOGIN, login));
            if (user == null) {
                return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
            }
            if ("1".equals(user.getStatus())) {//不可用
                return RespMsg.error(SecurityConstant.ACCOUNT_FREEZE, "账号已被冻结！");
            }
            //统一平台验证密码,此处不再验证sys_user表中的密码
//            if (!PwdUtil.verify(password, user.getPassword())) {
//                return RespMsg.error(SecurityConstant.ERROR_ACCOUNT_OR_PASSWORD, "账号或者密码不正确！");
//            }
            //List<SysRole> roles = rs.findRolesByUserId(user.getUserId());
            //List<String> apps = sas.findAppCodeByUserId(user.getUserId());
            List<SysRole> roles = new ArrayList<>();
            List<String> apps = new ArrayList<>();
            UserDetail detail = new UserDetail(user, roles, apps, false);
            //有移动端登录的住建部用户,记录客户来源
            detail.setUserSource(UserSource.ZJBYSER);
            //实体类记录用户基本信息
            detail.setId(user.getId());
            detail.setName(user.getName());
            detail.setLogin(user.getLogin());
            // 用户登录需要记录日志，这里要把当前用户设进去，否则切面拿不到信息
            SecurityUtil.setCurrentUser(detail);
            map.put("token", tp.createToken(detail));
            map.put("userName", user.getName());
            map.put("phone", user.getTel());
        }

        //map.put("ownDepart",user.getOwnDepart());
        return RespMsg.ok(map);
    }
}
