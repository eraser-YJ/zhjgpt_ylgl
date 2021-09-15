package com.digitalchina.common.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;

/**
 * <丰富集合工具类>
 * 
 * @author lihui
 * @since 2019年8月5日
 */
public class CollUtils extends CollUtil {

	/**
	 * 组装集合，提取重复代码
	 * 
	 * @param action
	 * @param list
	 * @return
	 */
	public static <S, T> List<T> instance(Function<S, T> action, List<S> list) {
		if (CollUtil.isEmpty(list))
			return null;

		List<T> target = new ArrayList<>();

		list.forEach(s -> {
			// 根据不同节点构建不同特征
			target.add(action.apply(s));
		});

		return target;
	}

	/**
	 * 新建一个List
	 * 
	 * @param          <T> 集合元素类型
	 * @param isLinked 是否新建LinkedList
	 * @param values   数组
	 * @return List对象
	 * @since 4.1.2
	 */
	@SafeVarargs
	public static <T> List<T> listFilterNull(T... values) {
		Boolean isLinked = false;
		if (ArrayUtil.isEmpty(values)) {
			return list(isLinked);
		}
		List<T> arrayList = isLinked ? new LinkedList<T>() : new ArrayList<T>(values.length);
		for (T t : values) {
			if (null != t) // 只是多个空值过滤
				arrayList.add(t);
		}
		return arrayList;
	}

}
