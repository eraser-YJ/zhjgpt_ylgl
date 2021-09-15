package com.digitalchina.gateway.filter;

import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.exception.SysSecurityException;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import com.digitalchina.modules.security.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户权限拦截
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requireRole = getRequireRole(handler);
		return checkAuth(requireRole);
	}

	private boolean checkAuth(String requireRole) {
		if (requireRole == null) { // 不需要验证
			return true;
		}
		UserDetail currentUser = SecurityUtil.currentUser();
		if (currentUser == null) {
			throw new SysSecurityException(SecurityConstant.UNAUTHORIZED, "用户未登录！");
		}
		if ("".equals(requireRole)) { // 如果只是写了注解，没有填具体角色，则只要登录了就可以访问
			return true;
		}
		String currentRoles = currentUser.getRoles();
		if (StringUtils.isBlank(currentRoles)) {
			throw new SysSecurityException(SecurityConstant.UNAUTHORIZED, "该用户没有访问权限！");
		}
		for (String role : StringUtils.split(requireRole, ",")) {
			// 只要存在一个角色就验证通过
			if (StringUtils.contains(currentRoles, StringUtils.trim(role))) {
				return true;
			}
		}
		throw new SysSecurityException(SecurityConstant.UNAUTHORIZED, "该用户没有访问权限！");
	}

	/**
	 * 获取方法的权限
	 * 
	 * @param handler
	 * @return
	 */
	private String getRequireRole(Object handler) {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			Authorize anno = method.getMethodAnnotation(Authorize.class);
			if (anno == null) {
				anno = method.getMethod().getDeclaringClass().getAnnotation(Authorize.class);
			}
			if (anno != null) {
				return anno.value();
			}
		}
		return null;
	}

}
