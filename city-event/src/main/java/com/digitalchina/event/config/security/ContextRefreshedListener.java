package com.digitalchina.event.config.security;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.digitalchina.modules.utils.AuthorizeUtil;

import cn.hutool.core.collection.CollUtil;

/**
 * 权限扫描
 * 
 * @author Warrior 2019年8月4日
 */
@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(ContextRefreshedListener.class);

	@Value("${spring.application.name}")
	private String applicationName;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<String, String> authMap = new HashMap<>();
		ApplicationContext applicationContext = event.getApplicationContext();
		Map<String, Object> restControllerBeanMap = applicationContext.getBeansWithAnnotation(RestController.class);
		Map<String, Object> controllerBeanMap = applicationContext.getBeansWithAnnotation(Controller.class);

		for (Entry<String, Object> kv : restControllerBeanMap.entrySet()) {
			for (Entry<String, String> temp : AuthorizeUtil.findAuth(kv.getValue()).entrySet()) {
				authMap.put(AuthorizeUtil.getAuthRedisKey(applicationName, temp.getKey()), temp.getValue());
			}
		}
		for (Entry<String, Object> kv : controllerBeanMap.entrySet()) {
			for (Entry<String, String> temp : AuthorizeUtil.findAuth(kv.getValue()).entrySet()) {
				authMap.put(AuthorizeUtil.getAuthRedisKey(applicationName, temp.getKey()), temp.getValue());
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("权限扫描结果为：" + authMap);
		}

		// 先删除原来的
		Set<String> keys = redisTemplate.keys(AuthorizeUtil.getAuthRedisKey(applicationName, "*"));
		if (CollUtil.isNotEmpty(keys)) {
			redisTemplate.delete(keys);
		}
		redisTemplate.opsForValue().multiSet(authMap);
	}

}
