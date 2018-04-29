package com.goodsogood.open.platform.service.controller;

import com.alibaba.fastjson.JSON;
import com.goodsogood.open.platform.service.aop.interceptor.annotation.Login;
import com.goodsogood.open.platform.service.biz.UserBiz;
import com.goodsogood.open.platform.service.common.context.LoginContext;
import com.goodsogood.open.platform.service.common.validation.ViolationHandler;
import com.goodsogood.open.platform.service.controller.dto.ResponseDTO;
import com.goodsogood.open.platform.service.controller.dto.UserDTO;
import com.goodsogood.open.platform.service.controller.group.Update;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

/**
 * Created by xin on 2018/4/24.
 * 此类为服务入口类,只参与数据验证和简单的数据转换,不参与业务逻辑
 * 访问路径原则上只使用path表达
 * 入参原则上只使用表单表达,出参使用json表达
 * 入参类型只能定义为8个基础类型和基于基础类型封装的pojo,入参个数暂不做要求,多个参数建议使用pojo包装,对于复杂的数据结构可以使用json字符串表达
 * 出参全部用Response包装,具体返回数据自定义,没有返回数据的用Void代替
 */
@RestController
@RequestMapping("/userDemo")
@Validated
public class UserDemoController {

	@Autowired
	private UserBiz userBiz;
	@Autowired
	private Validator validator;
	@Autowired
	private ViolationHandler violationHandler;

	@Login
	@RequestMapping("/findAllUser")
	public ResponseDTO<List<UserDTO>> findAllUser() {
		return ResponseDTO.success(Lists.newArrayList(new UserDTO().setId(1).setTrueName("xx").setAge(18)));
	}

	@Login
	@RequestMapping("/findUserByIds")
	public ResponseDTO<List<UserDTO>> findUserByIds(@NotEmpty String ids) {
		return ResponseDTO.success(Lists.newArrayList(new UserDTO().setId(1).setTrueName("xx").setAge(18)));
	}

	@Login
	@RequestMapping("/findUserById")
	public ResponseDTO<UserDTO> findUserById(@NotNull @Range(min = 1, max = 18) Integer id) {
		System.out.println(LoginContext.getLoginInfo());
		return userBiz.findUserById(id);
	}

	@Login
	@RequestMapping("/addUser")
	public ResponseDTO<Void> addUser(@Validated UserDTO userDTO) {
		return userBiz.addUser(userDTO);
	}

	@Login
	@RequestMapping("/updateUser")
	public ResponseDTO<Void> updateUser(@Validated({Update.class, Default.class}) UserDTO userDTO) {
		return userBiz.updateUser(userDTO);
	}

	@Login
	@RequestMapping("/updateUserByJson")
	public ResponseDTO<Void> updateUserByJson(@NotBlank String userJson) {
		UserDTO userDTO = JSON.parseObject(userJson, UserDTO.class);
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		String[] result = violationHandler.handleViolations(violations);
		if (!ArrayUtils.isEmpty(result)) {
			return ResponseDTO.error(result);
		}
		return userBiz.updateUser(userDTO);
	}

	@RequestMapping("/deleteUser")
	public ResponseDTO<Void> deleteUser(@NotNull Integer id) {
		return userBiz.deleteUser(id);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(URLEncoder.encode(JSON.toJSONString(new UserDTO()), "UTF-8"));
	}
}
