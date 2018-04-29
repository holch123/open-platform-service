package com.goodsogood.open.platform.service.controller.dto;

import com.goodsogood.open.platform.service.common.validation.constraint.Mobile;
import com.goodsogood.open.platform.service.controller.group.Update;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by xin on 2018/4/24.
 * 此类为服务入参类,所有setter必须返回this引用,方便链式调用
 * 必须覆盖toString方法并能够表达自己所携带的信息,参考此类的toString方法
 */
public class UserDTO {

	@NotNull(groups = {Update.class})
	private Integer id;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String trueName;
	@NotNull
	private Integer age;
	@NotBlank
	@Mobile
	private String mobile;

	public Integer getAge() {
		return age;
	}

	public UserDTO setAge(Integer age) {
		this.age = age;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public UserDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getMobile() {
		return mobile;
	}

	public UserDTO setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public UserDTO setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getTrueName() {
		return trueName;
	}

	public UserDTO setTrueName(String trueName) {
		this.trueName = trueName;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public UserDTO setUsername(String username) {
		this.username = username;
		return this;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
}
