package com.digitalchina.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.digitalchina.admin.aop.SecurityInterceptor;

/**
 * spring mvc 拦截器配置
 * 
 * @author warrior
 *
 * @since 2018年12月18日
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public HandlerInterceptor requestInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestInterceptor()).addPathPatterns("/**");
	}

}
