package com.goodsogood.open.platform.service.utils;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.springframework.util.ReflectionUtils.findMethod;

@SuppressWarnings("unchecked")
public abstract class ReflectUtils {

	public static Field findField(Object target, String fieldName) {
		Assert.notNull(target, "target is null");
		Assert.notNull(fieldName, "fieldName is null");
		return ReflectionUtils.findField(target.getClass(), fieldName);
	}

	public static <R> R getValue(Field field, Object target) {
		Assert.notNull(field, "field is null");
		Assert.notNull(target, "target is null");
		ReflectionUtils.makeAccessible(field);
		return (R) ReflectionUtils.getField(field, target);
	}

	public static void setValue(Field field, Object target, Object value) {
		Assert.notNull(field, "field is null");
		Assert.notNull(target, "target is null");
		ReflectionUtils.makeAccessible(field);
		ReflectionUtils.setField(field, target, value);
	}

	public static <R> R getValue(Object target, String fieldName) {
		Field field = findField(target, fieldName);
		Assert.notNull(field, fieldName + " not found");
		return (R) getValue(field, target);
	}

	public static <R> R invokeMethod(Object target, String methodName) {
		Assert.notNull(target, "target is null");
		Assert.hasText(methodName, "methodName is null");
		Method method = findMethod(target.getClass(), methodName);
		Assert.notNull(method, methodName + " not found");
		ReflectionUtils.makeAccessible(method);
		return (R) ReflectionUtils.invokeMethod(method, target);
	}

	public static <R> R invokeIgnoreMethodNotFound(Object target, String methodName) {
		Assert.notNull(target, "target is null");
		Assert.hasText(methodName, "methodName is null");
		Method method = findMethod(target.getClass(), methodName);
		if (method == null) {
			return null;
		}
		ReflectionUtils.makeAccessible(method);
		return (R) ReflectionUtils.invokeMethod(method, target);
	}

	public static <R> R invokeMethod(Object target, String methodName, Class[] paramTypes, Object... params) {
		Assert.notNull(target, "target is null");
		Assert.hasText(methodName, "methodName is null");
		Assert.notEmpty(paramTypes, "paramTypes is empty");
		Assert.notEmpty(params, "params is empty");
		Method method = findMethod(target.getClass(), methodName, paramTypes);
		Assert.notNull(method, methodName + " not found");
		ReflectionUtils.makeAccessible(method);
		return (R) ReflectionUtils.invokeMethod(method, target, params);
	}

	public static Method getInvokeMethod(Signature signature) {
		Assert.isInstanceOf(MethodSignature.class, signature);
		return ((MethodSignature) signature).getMethod();
	}

	public static <T extends Annotation> T getAnnotation(Signature signature, Class<T> annotationClass) {
		Assert.isInstanceOf(MethodSignature.class, signature);
		return ((MethodSignature) signature).getMethod().getAnnotation(annotationClass);
	}

	public static <T extends Annotation> T findParameterAnnotation(Method method, Class<T> annotationClass) {
		if (method == null || annotationClass == null) {
			return null;
		}
		Annotation[][] annotations = method.getParameterAnnotations();
		if (isEmpty(annotations)) {
			return null;
		}
		for (Annotation[] anns : annotations) {
			if (isEmpty(anns)) {
				continue;
			}
			for (Annotation ann : anns) {
				if (annotationClass.isInstance(ann)) {
					return (T) ann;
				}
			}
		}
		return null;
	}

	public static String getAnnotationSimpleName(Annotation annotation) {
		return annotation == null ? null : annotation.annotationType().getSimpleName();
	}

	public static Class getGenericParameterType(Method method) {
		Type[] parameterTypes = method.getGenericParameterTypes();
		if (!isEmpty(parameterTypes) && parameterTypes[0] instanceof ParameterizedType) {
			// 取第一个参数的泛型类型列表
			Type[] genericList = ((ParameterizedType) parameterTypes[0]).getActualTypeArguments();
			Type dataType = isEmpty(genericList) ? null : genericList[0];
			return dataType instanceof Class ? (Class) dataType : null;
		}
		return null;
	}
}