package com.goodsogood.open.platform.service.controller.dto;

import com.goodsogood.open.platform.service.common.enums.StatusCode;
import com.goodsogood.open.platform.service.exception.BizException;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.goodsogood.open.platform.service.common.enums.StatusCode.ERROR;
import static com.goodsogood.open.platform.service.common.enums.StatusCode.SUCCESS;

/**
 * 此类为公共返回类型,具体的返回数据用泛型T来包装
 * 此类不提供constructor、set方法,由build等一系列工厂方法代替
 */
public class ResponseDTO<T> {

	// 返回码
	private String code;
	// 返回描述
	private String msg;
	// 错误属性
	private String errorField;
	// 返回数据
	private T data;

	public String getCode() {
		return code;
	}

	public T getData() {
		return data;
	}

	public String getErrorField() {
		return errorField;
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}

	private ResponseDTO() {
	}

	public boolean isSuccess() {
		return SUCCESS.getCode().equals(code);
	}

	public static <T> ResponseDTO<T> success() {
		return success(null);
	}

	public static <T> ResponseDTO<T> success(T data) {
		return build(SUCCESS.getCode(), SUCCESS.getMsg(), null, data);
	}

	public static <T> ResponseDTO<T> error() {
		return error(ERROR.getCode(), ERROR.getMsg(), null);
	}

	public static <T> ResponseDTO<T> error(BizException bizEx) {
		return error(bizEx.getCode(), bizEx.getMsg(), null);
	}

	public static <T> ResponseDTO<T> error(StatusCode statusCode) {
		return error(statusCode.getCode(), statusCode.getMsg(), null);
	}

	public static <T> ResponseDTO<T> error(String[] validResult) {
		return error(validResult[0], validResult[1], validResult[2]);
	}

	public static <T> ResponseDTO<T> error(String code, String msg, String errorField) {
		return build(code, msg, errorField, null);
	}

	public static <T> ResponseDTO<T> build(String code, String msg, String errorField, T data) {
		ResponseDTO<T> responseDTO = new ResponseDTO<>();
		responseDTO.code = code;
		responseDTO.msg = msg;
		responseDTO.errorField = errorField;
		responseDTO.data = data;
		return responseDTO;
	}

	public static void main(String[] args) {
		System.out.println(success());
		System.out.println(success(UUID.randomUUID().toString()));
		System.out.println(error());
		System.out.println(error(new BizException("bizEx", "bizEx")));
		System.out.println(error(StatusCode.NOT_EMPTY));
		System.out.println(error(new String[]{"NOT_EMPTY", "不能为空", "name"}));
		System.out.println(error("code", "msg", "errorField"));
	}
}