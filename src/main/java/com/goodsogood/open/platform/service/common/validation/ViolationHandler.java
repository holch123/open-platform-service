package com.goodsogood.open.platform.service.common.validation;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Created by xin on 2018/4/25.
 */
public class ViolationHandler {

	private ValidCodeConvert codeConvert;

	public ViolationHandler(ValidCodeConvert codeConvert) {
		this.codeConvert = codeConvert;
	}

	// 处理验证结果,由{code,msg,errorField}构成
	public String[] handleBindingResult(BindingResult bindingResult) {
		FieldError fieldError = bindingResult.getFieldError();
		if (fieldError == null) {
			return null;
		}
		String code = codeConvert.convert(fieldError.getCode());
		return new String[]{code, fieldError.getDefaultMessage(), fieldError.getField()};
	}

	@SuppressWarnings("unchecked")
	public String[] handleUnknownViolations(Set<ConstraintViolation<?>> violations) {
		return handleViolations((Set) violations);
	}

	// 处理验证结果,由{code,msg,errorField}构成
	public <T> String[] handleViolations(Set<ConstraintViolation<T>> violations) {
		if (CollectionUtils.isEmpty(violations)) {
			return null;
		}
		ConstraintViolation<T> violation = violations.stream().findFirst().get();
		String code = codeConvert.convert(ViolationUtils.getAnnotationSimpleName(violation));
		String msg = violation.getMessage();
		String field = ViolationUtils.getFieldName(violation.getPropertyPath());
		return new String[]{code, msg, field};
	}
}
