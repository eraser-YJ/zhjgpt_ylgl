package com.digitalchina.modules.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;

/**
 * Feign 文件上传配置
 * 
 * @author warrior
 *
 */
@Configuration
public class FeignMultipartSupportConfig {

	// 支持pojo传输
	@Autowired
	private ObjectFactory<HttpMessageConverters> messageConverters;

	@Bean
	public Encoder multipartFormEncoder() {
		return new SpringFormEncoder(new SpringEncoder(messageConverters));
	}
}
