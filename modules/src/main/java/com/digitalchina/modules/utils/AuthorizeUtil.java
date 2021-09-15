package com.digitalchina.modules.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digitalchina.modules.constant.SecurityConstant;
import com.digitalchina.modules.security.Authorize;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 权限工具类
 * 
 * @author Warrior 2019年8月4日
 */
public class AuthorizeUtil {

	/**
	 * 根据controller对象，获取@Authrize注解标识的权限
	 * 
	 * @param controller
	 * @return <url,roles>
	 */
	public static Map<String, String> findAuth(Object controller) {
		Map<String, String> result = new HashMap<>();
		// 获取controller总路径
		Class<? extends Object> clazz = AopUtils.getTargetClass(controller);
		String classPath = findUrl(clazz, RequestMapping.class);

		String classRole = null;
		Authorize classAuth = AnnotationUtil.getAnnotation(clazz, Authorize.class);
		if (classAuth != null) {
			classRole = classAuth.value();
		}

		for (Method method : ReflectUtil.getMethods(clazz)) {
			if (checkApi(method)) { // 检查是否是api方法
				String methodRole = classRole;
				Authorize methodAuth = AnnotationUtil.getAnnotation(method, Authorize.class);
				if (methodAuth != null) {
					methodRole = methodAuth.value();
				}
				if (methodRole != null) { // 该url需要验证
					String url = findUrl(method);
					result.put(formateUrl(classPath + "/" + url), methodRole);
				}
			}
		}

		return result;
	}

	/**
	 * 检查是否是controller接口方法
	 * 
	 * @param method
	 * @return
	 */
	private static boolean checkApi(Method method) {
		if (AnnotationUtil.getAnnotation(method, GetMapping.class) != null
				|| AnnotationUtil.getAnnotation(method, PostMapping.class) != null
				|| AnnotationUtil.getAnnotation(method, RequestMapping.class) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 格式化请求路径
	 * 
	 * @param path
	 * @return
	 */
	public static String formateUrl(String path) {
		// 去除开头的\或者/
		path = path.replaceAll("^[\\/]+", StrUtil.EMPTY);
		// 替换多个\或/为单个/
		path = path.replace("\\", "/").replaceAll("//+", "/");
		// 去除结尾的\或者/
		path = path.replaceAll("[\\/]+$", StrUtil.EMPTY);
		return path;
	}

	/**
	 * 查找@RequestMapping，@PostMapping，@GetMapping标识的URL
	 * 
	 * @param annotationEle
	 * @return
	 */
	private static String findUrl(AnnotatedElement annotationEle) {
		String url = findUrl(annotationEle, GetMapping.class);
		if (StrUtil.isBlank(url)) {
			url = findUrl(annotationEle, PostMapping.class);
		}
		if (StrUtil.isBlank(url)) {
			url = findUrl(annotationEle, RequestMapping.class);
		}
		return url;
	}

	/**
	 * 查找某个标识的url
	 * 
	 * @param annotationEle
	 * @param annotationType
	 * @return
	 */
	private static String findUrl(AnnotatedElement annotationEle, Class<? extends Annotation> annotationType) {
		String[] rvs = AnnotationUtil.getAnnotationValue(annotationEle, annotationType, "value");
		if (rvs != null && rvs.length > 0) {
			return rvs[0];
		} else {
			String[] rps = AnnotationUtil.getAnnotationValue(annotationEle, annotationType, "path");
			if (rps != null && rps.length > 0)
				return rps[0];
		}
		return "";
	}

	/**
	 * 获取请求路径的系统标志 citysign/test/redis -> sitysign
	 * 
	 * @param url
	 * @return
	 */
	public static String getAppName(String url) {
		if (StrUtil.isNotBlank(url)) {
			url = formateUrl(url);
			return StrUtil.subBefore(url, "/", false);
		}
		return null;
	}

	/**
	 * 用户信息保存redis key产生规则
	 * 
	 * @param token
	 * @return
	 */
	public static String getUserRedisKey(String token) {
		return SecurityConstant.AUTH_TOKEN_REDIS_KEY_PERFIX + ":" + token;
	}

	/**
	 * 用户ID保存redis key产生规则
	 * 
	 * @param app
	 * @param userId
	 * @return
	 */
	public static String getUserTokenRedisKey(String app, String userId) {
		return SecurityConstant.AUTH_USER_ID_REDIS_KEY_PERFIX + ":" + app + ":" + userId;
	}

	/**
	 * 根据url获取redis保存的key
	 * 
	 * @param url
	 * @return
	 */
	public static String getAuthRedisKey(String url) {
		url = formateUrl(url);
		return getAuthRedisKey(getAppName(url), StrUtil.subAfter(url, "/", false));
	}

	/**
	 * 根据appName和去除appname的url获取redis保存的key
	 * 
	 * @param appName
	 * @param url     去除appname的url
	 * @return
	 */
	public static String getAuthRedisKey(String appName, String url) {
		url = formateUrl(url);
		return SecurityConstant.AUTH_URL_REDIS_KEY_PERFIX + ":" + appName + ":" + url;
	}

}
