package com.goodsogood.open.platform.service.common.validation;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.util.Assert;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Arrays;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;

/**
 * Created by xin on 2018/4/26.
 * 此类为spring验证体系的一部分,为了和aop分层设计融合,对其进行了扩展
 */
public class ValidationProcessor extends MethodValidationPostProcessor {

	// 验证aop排序变量
	private int validatorAopOrder = 0;

	public ValidationProcessor setValidatorAopOrder(int validatorAopOrder) {
		this.validatorAopOrder = validatorAopOrder;
		return this;
	}

	// 此BeanProcessor初始完成化后,设置此advisor的执行顺序
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.isInstanceOf(AbstractPointcutAdvisor.class, super.advisor);
		((AbstractPointcutAdvisor) super.advisor).setOrder(validatorAopOrder);
	}

	// bean后置处理,对bean附加此BeanProcessor携带的advisor并对advisor排序
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		bean = super.postProcessAfterInitialization(bean, beanName);
		if (bean instanceof Advised) {
			Advised advised = (Advised) bean;
			if (!advised.isFrozen() && isEligible(AopUtils.getTargetClass(bean)) && !isEmpty(advised.getAdvisors())) {
				Advisor[] advisors = advised.getAdvisors();
				AnnotationAwareOrderComparator.sort(advisors);
				Arrays.stream(advisors).forEach(advised::removeAdvisor);
				Arrays.stream(advisors).forEach(advised::addAdvisor);
			}
		}
		return bean;
	}
}
