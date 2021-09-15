package com.digitalchina.admin.log;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 权限日志注解
 * 
 * @author warrior
 *
 * @since 2019年3月1日
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLogger {

	/**
	 * 填写功能名称  支持spel表达式
	 *
	 *举个栗子：
	 * @SysLogger("自定义#{你的spel表达式}日志")
	 * #{ }是定界符
	 * @return
	 */
	public String value() default "";
}
