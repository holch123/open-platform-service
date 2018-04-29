package com.goodsogood.open.platform.service.dao;

import com.goodsogood.open.platform.service.dao.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by xin on 2018/4/24.
 * 此类为数据执行类,仅跟读写数据库相关,不参与任何业务逻辑
 * 以下伪代码,请自行实现,不限于hibernate、mybatis等orm框架,原则上不允许混用
 */
@Repository
public class UserDao {

	private ThreadLocalRandom random = ThreadLocalRandom.current();

	public UserEntity findUserById(Integer id) {
		return new UserEntity().setId(random.nextInt(10)).setName("张3").setAge(random.nextInt(18));
	}

	public int addUser(UserEntity userEntity) {
		return random.nextInt() % 2 == 0 ? 1 : 0;
	}

	public int updateUser(UserEntity userEntity) {
		return random.nextInt() % 2 == 0 ? 1 : 0;
	}

	public int deleteUser(Integer id) {
		return random.nextInt() % 2 == 0 ? 1 : 0;
	}
}
