package com.digitalchina.zhjg.szss.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({ "dev", "test","remotedev" })
@Configuration // 标记配置类
@EnableSwagger2 // 开启在线接口文档
public class Swagger2Config {

	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("管理系统接口API")
				.apiInfo(new ApiInfoBuilder().title("智慧建管-市政设施接口文档").description("智慧建管-市政设施接口文档")
						.contact(new Contact("Warrior", null, null)).version("版本号:1.0").build())
				.select().apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any()).build();
	}

}
