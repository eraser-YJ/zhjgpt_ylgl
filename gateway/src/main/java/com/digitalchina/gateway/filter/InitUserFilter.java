package com.digitalchina.gateway.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.SecurityUtil;

public class InitUserFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		SecurityUtil.setCurrentToken(resolveToken(req));
		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		return request.getHeader(SecurityConstant.AUTH_TOKEN);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
