package com.digitalchina.gateway.filter;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器注册bean 如果filter里面需要注入bean，使用@WebFilter放到tomcat里运行的时候不会有作用
 * 需要通过sring注册filter https://blog.csdn.net/tyyytcj/article/details/73742000
 * 
 * @author warrior 2018年5月16日
 */
@Configuration
public class FilterConfig {

	@Bean
	public Filter initUserFilter() {
		return new InitUserFilter();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean initUserFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(initUserFilter());
		registrationBean.addUrlPatterns("/*");
		registrationBean.setName("initUserFilter");
		registrationBean.setOrder(0);
		return registrationBean;
	}

}
