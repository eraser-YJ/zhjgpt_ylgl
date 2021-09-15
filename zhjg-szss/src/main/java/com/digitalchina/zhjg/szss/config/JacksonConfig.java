package com.digitalchina.zhjg.szss.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * mvc序列化Jackson 配置
 * 
 * @author warrior
 *
 * @since 2018年12月4日
 */
@Configuration
public class JacksonConfig {

	@Bean
	public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = converter.getObjectMapper();
		/**
		 * 序列换成json时,将所有的long变成string 因为js中得数字类型不能包含所有的java long值
		 */
		SimpleModule simpleModule = new SimpleModule();
		// simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		// simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		/**
		 * 默认日期格式， 需要其他格式，在对应的字段上配置@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		 */
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(formatter);
		objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		//字符串支持解析换行符号
		objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		converter.setObjectMapper(objectMapper);
		return converter;
	}
}
