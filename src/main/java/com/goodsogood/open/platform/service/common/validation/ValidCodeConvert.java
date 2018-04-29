package com.goodsogood.open.platform.service.common.validation;

import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

import static com.goodsogood.open.platform.service.common.enums.StatusCode.UNKNOWN_CODE;
import static com.goodsogood.open.platform.service.common.enums.StatusCode.values;
import static java.text.MessageFormat.format;
import static java.util.Arrays.stream;

/**
 * Created by xin on 2018/4/26.
 */
public interface ValidCodeConvert {

	// 将验证注解类名转为状态码
	String convert(String constraintName);

	class DefaultCodeConvert implements ValidCodeConvert {
		// Map<constraintName,code>
		private Map<String, String> codeMap = new HashMap<>();

		public DefaultCodeConvert() {
			stream(values()).forEach(statusCode -> {
				if (!CollectionUtils.isEmpty(statusCode.getAlias())) {
					statusCode.getAlias().stream().forEach(alias -> codeMap.put(alias, statusCode.getCode()));
				}
			});
		}

		@Override
		public String convert(String constraintName) {
			String code = codeMap.get(constraintName);
			return code != null ? code : format(UNKNOWN_CODE.getCode(), constraintName);
		}
	}
}
