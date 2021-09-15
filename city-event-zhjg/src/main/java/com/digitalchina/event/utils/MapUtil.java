package com.digitalchina.event.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lichunlong
 * @Description: Map对象工具类
 */
public class MapUtil {

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

}
