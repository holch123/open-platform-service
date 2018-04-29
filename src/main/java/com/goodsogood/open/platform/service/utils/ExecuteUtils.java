package com.goodsogood.open.platform.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017/8/10.
 */
public abstract class ExecuteUtils {

	private static final Logger log = LoggerFactory.getLogger(ExecuteUtils.class);

	public static void execNoResult(TaskNoResult task) {
		try {
			task.execute();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T> T execWithResult(TaskWithResult<T> task) {
		return execWithResult(task, null);
	}

	public static <T> T execWithResult(TaskWithResult<T> task, T defaultResult) {
		try {
			return task.execute();
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
		return defaultResult;
	}

	public interface TaskNoResult {
		void execute() throws Throwable;
	}

	public interface TaskWithResult<T> {
		T execute() throws Throwable;
	}
}
