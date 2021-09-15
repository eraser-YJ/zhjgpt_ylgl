package com.digitalchina.event.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;

@Configuration
// @MapperScan("com.baomidou.springboot.mapper*")//这个注解，作用相当于下面的@Bean
// MapperScannerConfigurer，2者配置1份即可
public class MybatisPlusConfig {

	/**
	 * 相当于顶部的： {@code @MapperScan("com.baomidou.springboot.mapper*")}
	 * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		scannerConfigurer.setBasePackage("com.digitalchina.event.mid.mapper*");
		return scannerConfigurer;
	}

	/**
	 * 性能分析拦截器，不建议生产使用
	 */
	@Bean
	@Profile({ "dev", "test" })
	public PerformanceInterceptor performanceInterceptor() {
		return new PerformanceInterceptor();
	}

	/**
	 * 分页插件，自动识别数据库类型
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		return page;
	}

}
