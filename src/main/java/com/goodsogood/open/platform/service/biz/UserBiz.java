package com.goodsogood.open.platform.service.biz;

import com.goodsogood.open.platform.service.common.enums.StatusCode;
import com.goodsogood.open.platform.service.controller.dto.ResponseDTO;
import com.goodsogood.open.platform.service.controller.dto.UserDTO;
import com.goodsogood.open.platform.service.dao.UserDao;
import com.goodsogood.open.platform.service.dao.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.goodsogood.open.platform.service.controller.dto.ResponseDTO.error;
import static com.goodsogood.open.platform.service.controller.dto.ResponseDTO.success;
import static com.goodsogood.open.platform.service.utils.BeanUtil.copyProperties;

/**
 * Created by xin on 2018/4/24.
 * 此类为业务执行类,所有的业务逻辑请放在此处,尽量不要分散业务逻辑
 * 过于复杂的业务逻辑可以将其分散成多个类组合完成(分散的类建一个子包放在biz包下面,包名根据业务命名),并由此类做主线控制
 */
@Service
public class UserBiz {

	@Autowired
	private UserDao userDao;

	public ResponseDTO<UserDTO> findUserById(Integer id) {
		UserEntity userEntity = userDao.findUserById(id);
		return success(copyProperties(userEntity, UserDTO.class));
	}

	public ResponseDTO<Void> addUser(UserDTO userDTO) {
		UserEntity userEntity = copyProperties(userDTO, UserEntity.class);
		int effect = userDao.addUser(userEntity);
		// 此处返回通用错误是因为不知道为什么数据库操作失败,正常情况下只会成功
		// ------------------以下为其他情况处理说明---------------------
		// 如果name不允许重复,但name就是重复且插入失败了,则必须catch异常并转为具体的状态码返回
		// 这里并非要求所有代码都在此处catch异常,这只会增加代码的复杂和可读性,仅仅存在可预知的异常时,才catch并处理成相应的状态码返回
		return effect > 0 ? success() : error(StatusCode.ERROR);
	}

	public ResponseDTO<Void> updateUser(UserDTO userDTO) {
		UserEntity userEntity = copyProperties(userDTO, UserEntity.class);
		int effect = userDao.updateUser(userEntity);
		// 处理方式同上
		return effect > 0 ? success() : error(StatusCode.ERROR);
	}

	public ResponseDTO<Void> deleteUser(Integer id) {
		int effect = userDao.deleteUser(id);
		// 处理方式同上
		return effect > 0 ? success() : error(StatusCode.ERROR);
	}
}
