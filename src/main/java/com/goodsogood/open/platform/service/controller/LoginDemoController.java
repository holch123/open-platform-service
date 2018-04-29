package com.goodsogood.open.platform.service.controller;

import com.goodsogood.open.platform.service.common.enums.StatusCode;
import com.goodsogood.open.platform.service.controller.dto.ResponseDTO;
import com.goodsogood.open.platform.service.controller.dto.UserDTO;
import com.goodsogood.open.platform.service.utils.RequestUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.goodsogood.open.platform.service.common.context.LoginContext.LOGIN_KEY;

/**
 * Created by xin on 2018/4/27.
 */
@RestController
@RequestMapping("/loginDemo")
@Validated
public class LoginDemoController {

	@RequestMapping("/login")
	public ResponseDTO<String> login(@NotBlank String username, @NotBlank String password) {
		if (username.equals("u") && password.equals("p")) {
			RequestUtils.getSession().setAttribute(LOGIN_KEY, new UserDTO());
			return ResponseDTO.success(UUID.randomUUID().toString());
		} else {
			return ResponseDTO.error(StatusCode.USER_PWD_ERROR);
		}
	}
}
