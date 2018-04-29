package com.goodsogood.open.platform.service.aop;

import com.goodsogood.open.platform.service.aop.order.AopOrder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(AopOrder.EnterOrder.CONTROLLER_AOP)
public class ControllerAop {

	@Pointcut("execution(public com.goodsogood.open.platform.service.controller.dto.ResponseDTO com.goodsogood.open.platform.service.controller.*.*(..))")
	public void defaultPointcut() {
	}

	@Around("defaultPointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		Object returnVal = pjp.proceed();
		LogHandler.print(returnVal, null);
		return returnVal;
	}
}
