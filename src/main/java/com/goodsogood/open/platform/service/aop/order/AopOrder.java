package com.goodsogood.open.platform.service.aop.order;

/**
 * Created by Xin.L on 2017/8/31.
 * 定义aop的执行顺序，数值越大越后执行
 * 例：有aop1 aop2 aop3，执行顺序是aop1->aop2->aop3，里层的aop不处理任何异常，由最外层的aop统一处理
 * 不要占用Integer.MIN_VALUE ~ Integer.MIN_VALUE+10、Integer.MAX_VALUE-10 ~ Integer.MAX_VALUE这两个区间的值，spring框架会使用
 * 0~9供 EnterAop 使用
 * 10~19供 CommonAop 使用
 * 20以上供 BizAop 使用
 * EnterAop -> CommonAop -> BizAop
 */
public interface AopOrder {

	interface EnterOrder {
		int VALID_AOP = 0;
		int CONTROLLER_AOP = 1;
	}

	interface CommonOrder {

	}

	interface BizOrder {
		int LOGIN_AOP = 20;
	}
}
