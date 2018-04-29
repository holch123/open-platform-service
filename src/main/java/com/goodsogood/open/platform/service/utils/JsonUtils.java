package com.goodsogood.open.platform.service.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */
public abstract class JsonUtils {

	public static String toJSON(Object obj) {
		return toJSONString(obj, false, null);
	}

	public static String toPrettyJSON(Object obj) {
		return toJSONString(obj, true, obj == null ? null : obj.toString());
	}

	public static String toJSONString(Object obj, boolean pretty, String defaultResult) {
		return ExecuteUtils.execWithResult(() -> JSON.toJSONString(obj, pretty), defaultResult);
	}

	public static <T> List<T> parseArray(Class<T> clazz, String jsonArray) {
		return ExecuteUtils.execWithResult(() -> JSON.parseArray(jsonArray, clazz));
	}
}
