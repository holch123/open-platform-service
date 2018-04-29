package com.goodsogood.open.platform.service.utils;

import org.springframework.beans.BeanUtils;

import static org.springframework.beans.BeanUtils.instantiate;

/**
 * Created by xin on 2018/4/24.
 */
public abstract class BeanUtil {

	public static <T> T copyProperties(Object source, Class<T> targetClazz) {
		if (source == null || targetClazz == null)
			return null;
		T target = instantiate(targetClazz);
		BeanUtils.copyProperties(source, target);
		return target;
	}
}
