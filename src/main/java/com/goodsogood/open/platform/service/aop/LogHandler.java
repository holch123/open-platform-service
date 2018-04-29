package com.goodsogood.open.platform.service.aop;

import com.goodsogood.open.platform.service.exception.BizException;
import com.goodsogood.open.platform.service.utils.JsonUtils;
import com.goodsogood.open.platform.service.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.goodsogood.open.platform.service.utils.ExecuteUtils.execNoResult;

/**
 * Created by xin on 2018/4/26.
 */
public abstract class LogHandler {

	private static final String LOG_FORMAT = "{}";
	private static final Logger log = LoggerFactory.getLogger(LogHandler.class);

	// 打印日志
	public static void print(Object response, Throwable e) {
		execNoResult(() -> {
			Map<String, Object> map = new HashMap<>();
			map.put("path", getPath());
			map.put("request", getRequest());
			map.put("response", response);
			if (notPrintEx(e)) {
				log.info(LOG_FORMAT, JsonUtils.toPrettyJSON(map));
			} else {
				log.error(LOG_FORMAT, JsonUtils.toPrettyJSON(map), e);
			}
		});
	}

	private static String getPath() {
		return RequestUtils.getRequest().getRequestURI();
	}

	private static Map<String, Object> getRequest() {
		Map<String, String[]> parameterMap = RequestUtils.getRequest().getParameterMap();
		if (CollectionUtils.isEmpty(parameterMap)) {
			return Collections.emptyMap();
		}
		Map<String, Object> argsMap = new HashMap<>();
		parameterMap.entrySet().forEach(entry -> {
			String[] values = entry.getValue();
			argsMap.put(entry.getKey(), values.length <= 1 ? values[0] : values);
		});
		return argsMap;
	}

	private static boolean notPrintEx(Throwable e) {
		return e == null ||
				e instanceof BindException ||
				e instanceof ConstraintViolationException ||
				(e instanceof BizException && e.getCause() == null);
	}
}
