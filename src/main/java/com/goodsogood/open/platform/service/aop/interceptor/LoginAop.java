package com.goodsogood.open.platform.service.aop.interceptor;

import com.goodsogood.open.platform.service.aop.order.AopOrder;
import com.goodsogood.open.platform.service.common.context.LoginContext;
import com.goodsogood.open.platform.service.controller.dto.ResponseDTO;
import com.goodsogood.open.platform.service.controller.dto.UserDTO;
import com.goodsogood.open.platform.service.utils.RequestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.goodsogood.open.platform.service.common.context.LoginContext.LOGIN_KEY;
import static com.goodsogood.open.platform.service.common.enums.StatusCode.NOT_LOGIN;

/**
 * Created by Xin.L on 2017/7/10.
 * 登录拦截
 */
@Aspect
@Component
@Order(AopOrder.BizOrder.LOGIN_AOP)
public class LoginAop {

	@Pointcut("execution(public com.goodsogood.open.platform.service.controller.dto.ResponseDTO com.goodsogood.open.platform.service.controller.*.*(..))")
	public void defaultPointcut() {
	}

	@Pointcut("@annotation(com.goodsogood.open.platform.service.aop.interceptor.annotation.Login)")
	public void loginPointcut() {
	}

	@Around("defaultPointcut() && loginPointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		UserDTO userDTO = (UserDTO) RequestUtils.getSession().getAttribute(LOGIN_KEY);
		try {
			if (userDTO != null) {
				LoginContext.setLoginInfo(userDTO);
				return pjp.proceed();
			} else {
				return ResponseDTO.error(NOT_LOGIN);
			}
		} finally {
			LoginContext.remove();
		}
	}
}
