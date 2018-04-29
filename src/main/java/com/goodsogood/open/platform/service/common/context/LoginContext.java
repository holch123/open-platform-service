package com.goodsogood.open.platform.service.common.context;

import com.goodsogood.open.platform.service.controller.dto.UserDTO;

/**
 * Created by xin on 2018/4/27.
 */
public class LoginContext {

	public static final String LOGIN_KEY = "LOGIN_KEY";

	private static final ThreadLocal<UserDTO> LOGIN_INFO = new ThreadLocal<>();

	public static UserDTO getLoginInfo() {
		return LOGIN_INFO.get();
	}

	public static void setLoginInfo(UserDTO userDTO) {
		LOGIN_INFO.set(userDTO);
	}

	public static void remove() {
		LOGIN_INFO.remove();
	}
}
