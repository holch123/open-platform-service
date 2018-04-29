package com.goodsogood.open.platform.service.exception;

import java.text.MessageFormat;

/**
 * Created by Xin.L on 2017/7/10.
 * 此类为统一异常类,可以在controler、biz、dao等必要的时候抛出此异常
 * 原则上不派生其他任何子类
 */
public class BizException extends RuntimeException {

	private String code;
	private String msg;

	public BizException(String code, String msg) {
		super(MessageFormat.format("[code={0}, msg={1}]", code, msg));
		this.code = code;
		this.msg = msg;
	}

	public BizException(String code, String msg, Throwable cause) {
		super(MessageFormat.format("[code={0}, msg={1}]", code, msg), cause);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public BizException setCode(String code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public BizException setMsg(String msg) {
		this.msg = msg;
		return this;
	}
}
