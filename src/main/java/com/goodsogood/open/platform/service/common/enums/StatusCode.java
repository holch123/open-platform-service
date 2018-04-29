package com.goodsogood.open.platform.service.common.enums;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Set;

/**
 * Created by Xin.L on 2017/4/7.
 * 业务状态码集合
 */
public enum StatusCode {

	// 公共状态码，其他业务不得占用
	SUCCESS("SUCCESS", "success"),
	ERROR("ERROR", "error"),
	UNKNOWN_CODE("UNKNOWN_CODE[{0}]", "未知状态码"),
	NOT_NULL("NOT_NULL", "不能为null", "NotNull"),
	NOT_EMPTY("NOT_EMPTY", "不能为空", "NotEmpty", "NotBlank"),
	RANGE_ERROR("RANGE_ERROR", "范围错误", "Size", "Range"),
	MOBILE_ERROR("MOBILE_ERROR", "手机号格式错误", "Mobile"),
	// 业务状态码
	NOT_LOGIN("NOT_LOGIN", "没有登录"),
	USER_PWD_ERROR("USER_PWD_ERROR", "用户名或密码错误"),;

	// 状态码
	private String code;
	// 状态码别名,对应着验证注解的类名
	private Set<String> alias;
	// 状态码描述
	private String msg;

	StatusCode(String code, String msg, String... alias) {
		this.code = code;
		this.msg = msg;
		this.alias = ArrayUtils.isEmpty(alias) ? null : Sets.newHashSet(alias);
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Set<String> getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
}
