package com.goodsogood.open.platform.service.common.config;

import com.goodsogood.open.platform.service.aop.order.AopOrder;
import com.goodsogood.open.platform.service.common.validation.ValidCodeConvert;
import com.goodsogood.open.platform.service.common.validation.ValidationProcessor;
import com.goodsogood.open.platform.service.common.validation.ViolationHandler;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;

/**
 * Created by xin on 2018/4/24.
 * 所有配置请放在此类或此包下面
 */
@Configuration
public class OpenPlatformServiceConfig {

	@Bean
	public ViolationHandler violationHandler() {
		return new ViolationHandler(new ValidCodeConvert.DefaultCodeConvert());
	}

	@Bean
	@SuppressWarnings("SpringJavaAutowiringInspection")
	public static MethodValidationPostProcessor methodValidationPostProcessor(Environment environment, Validator validator) {
		ValidationProcessor processor = new ValidationProcessor();
		processor.setProxyTargetClass(determineProxyTargetClass(environment));
		processor.setValidator(validator);
		processor.setValidatorAopOrder(AopOrder.EnterOrder.VALID_AOP);
		return processor;
	}

	private static boolean determineProxyTargetClass(Environment environment) {
		RelaxedPropertyResolver resolver = new RelaxedPropertyResolver(environment, "spring.aop.");
		Boolean value = resolver.getProperty("proxyTargetClass", Boolean.class);
		return (value != null ? value : true);
	}
}
