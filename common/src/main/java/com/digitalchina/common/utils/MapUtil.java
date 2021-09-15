package com.digitalchina.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lichunlong
 * @Description: Map对象工具类
 */
public class MapUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, Object> getHashMap(Object... obj) {
		Map resultMap = new HashMap();
		if (obj == null || obj.length % 2 != 0) {
			return resultMap;
		}
		for (int i = 0; i < obj.length; i = i + 2) {
			String key = obj[i].toString();
			resultMap.put(key, obj[i + 1]);
		}

		return resultMap;
	}

	// Map转Object
	public static Object getBean(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;
		Object obj = beanClass.newInstance();
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}
			field.setAccessible(true);
			if (map.containsKey(field.getName())) {
				field.set(obj, map.get(field.getName()));
			}
		}
		return obj;
	}

}
