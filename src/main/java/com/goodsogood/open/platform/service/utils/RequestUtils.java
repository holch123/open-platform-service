package com.goodsogood.open.platform.service.utils;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Xin.L on 2017/11/21.
 */
public abstract class RequestUtils {

	public static HttpServletRequest getRequest() {
		return getServletRequestAttributes().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return getServletRequestAttributes().getResponse();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static ServletRequestAttributes getServletRequestAttributes() {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);
		return (ServletRequestAttributes) requestAttributes;
	}
}
