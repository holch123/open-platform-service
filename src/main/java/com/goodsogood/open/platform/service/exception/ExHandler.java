package com.goodsogood.open.platform.service.exception;

import com.goodsogood.open.platform.service.aop.LogHandler;
import com.goodsogood.open.platform.service.common.validation.ViolationHandler;
import com.goodsogood.open.platform.service.controller.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

import static com.goodsogood.open.platform.service.controller.dto.ResponseDTO.error;

/**
 * Created by xin on 2018/4/26.
 * 统一异常处理和打印日志,对于未知异常,返回通用错误
 */
@RestControllerAdvice
public class ExHandler {

	@Autowired
	private ViolationHandler violationHandler;

	@ExceptionHandler
	public ResponseDTO<Void> exHandler(Throwable e) {
		ResponseDTO<Void> responseDTO = null;
		try {
			if (e instanceof BindException) {
				responseDTO = error(violationHandler.handleBindingResult((BindingResult) e));
				return responseDTO;
			}
			if (e instanceof ConstraintViolationException) {
				responseDTO = error(violationHandler.handleUnknownViolations(((ConstraintViolationException) e).getConstraintViolations()));
				return responseDTO;
			}
			if (e instanceof BizException) {
				responseDTO = error((BizException) e);
				return responseDTO;
			}
			responseDTO = error();
			return responseDTO;
		} finally {
			LogHandler.print(responseDTO, e);
		}
	}
}
