package com.digitalchina.modules.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 权限拦截注解
 * 
 * @author warrior
 *
 * @since 2018年12月17日
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Authorize {

	/**
	 * 填上角色的名称，多个角色用逗号隔开（或的关系）；如果不填，则表示只要登录就可以访问
	 * 
	 * @return
	 */
	public String value() default "";
}
