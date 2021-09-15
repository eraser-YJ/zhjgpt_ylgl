package com.digitalchina.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;

/**
 * 服务配置中心
 * 
 * @author warrior
 *
 * @since 2019年8月5日
 */
@EnableConfigServer
@EnableDiscoveryClient
@ComponentScan(basePackages = { "com.digitalchina" })
@SpringBootApplication
public class ServiceConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceConfigApplication.class, args);
	}
}
