package com.goodsogood.open.platform.service.dao.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by xin on 2018/4/24.
 */
public class UserEntity {

	private Integer id;
	private String name;
	private Integer age;

	public Integer getAge() {
		return age;
	}

	public UserEntity setAge(Integer age) {
		this.age = age;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public UserEntity setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public UserEntity setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
	}
}
