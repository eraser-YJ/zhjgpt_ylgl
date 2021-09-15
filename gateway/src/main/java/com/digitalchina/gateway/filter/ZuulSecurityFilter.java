package com.digitalchina.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.digitalchina.common.utils.JsonMapper;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.UserDetail;
import com.digitalchina.modules.utils.AuthorizeUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import cn.hutool.core.util.StrUtil;

/**
 * 权限过滤器
 * 
 * @author Warrior 2019年8月4日
 */
@Component
@Order(1)
public class ZuulSecurityFilter extends ZuulFilter {

	private static final Logger log = LoggerFactory.getLogger(ZuulSecurityFilter.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public Object run() throws ZuulException {
		HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
		checkAuth(request.getRequestURI());
		return null;
	}

	/**
	 * 检查权限
	 * 
	 * @param requestURI
	 */
	private void checkAuth(String requestURI) {
		String redisUrlKey = formateRedisUrlKey(requestURI);
		Object roleObj = redisTemplate.opsForValue().get(redisUrlKey);

		if (roleObj == null) { // 不需要验证
			return;
		}

		UserDetail currentUser = SecurityUtil.currentUser();
		if (currentUser == null) {
			errorHandle("该用户未登陆！没有权限访问路径：" + requestURI);
			return;
		}
		//智慧建管app---不需要权限控制
		if(currentUser.getUserSource()!=null){
			return;
		}
		// 检查是否有权限访问系统
		String appCode = AuthorizeUtil.getAppName(requestURI);
		if (!StrUtil.containsAny(currentUser.getApps(), appCode)) {
			errorHandle("该用户没有权限访问该系统，appCode=" + appCode);
			return;
		}

		if ("".equals(roleObj)) { // 如果只是写了注解，没有填具体角色，则只要登录了就可以访问
			return;
		}
		String currentRoles = currentUser.getRoles();
		if (StrUtil.isBlank(currentRoles)) {
			errorHandle("该用户没有访问权限访问路径：" + requestURI + ",用户信息=" + currentUser + ",url roles=" + roleObj);
			return;
		}
		if (StrUtil.containsAny(roleObj.toString(), SecurityConstant.FORBIDDEN)) {
			errorHandle("该用户没有访问权限访问路径：" + requestURI + ",该路径只允许内部使用,用户信息=" + currentUser + ",url roles=" + roleObj);
			return;
		}
		for (String role : StrUtil.split(roleObj.toString(), ",")) {
			// 只要存在一个角色就验证通过
			if (StrUtil.containsAny(currentRoles, StrUtil.trim(role))) {
				return;
			}
		}
		errorHandle("该用户没有访问权限访问路径：" + requestURI + ",用户信息=" + currentUser + ",url roles=" + roleObj);

	}

	/**
	 * 返回错误信息
	 * 
	 * @param msg
	 */
	private void errorHandle(String msg) {
		log.error(msg);
		RequestContext ctx = RequestContext.getCurrentContext();
		ctx.setSendZuulResponse(false);
		ctx.setResponseStatusCode(SecurityConstant.UNAUTHORIZED);
		ctx.setResponseBody(JsonMapper.toJsonString(RespMsg.error(SecurityConstant.UNAUTHORIZED, msg)));
		ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
	}

	/**
	 * 转换为redis的urlKey
	 * 
	 * @param requestURI
	 * @return
	 */
	private String formateRedisUrlKey(String requestURI) {
		return AuthorizeUtil.getAuthRedisKey(requestURI);
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
