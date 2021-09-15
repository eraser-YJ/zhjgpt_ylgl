package com.digitalchina.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <嵌入式容器启动类>
 * 
 * @author lichunlong
 * @since 2019年09月04日
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = { "com.digitalchina" })
@ComponentScan(basePackages = { "com.digitalchina" }) // 依赖了文件处理的模块包路径不同默认扫描不到需修改包的扫描路径
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableTransactionManagement
@EnableCaching
public class ZhjgEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhjgEventApplication.class, args);
	}

}
