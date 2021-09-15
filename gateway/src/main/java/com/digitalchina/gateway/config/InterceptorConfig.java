package com.digitalchina.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.digitalchina.gateway.filter.SecurityInterceptor;

/**
 * spring mvc 拦截器配置
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**");
	}

}
