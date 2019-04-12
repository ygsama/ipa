package io.github.ygsama.oauth2server.web.impl;


import io.github.ygsama.oauth2server.domain.SysUserDO;
import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.UserDTO;
import io.github.ygsama.oauth2server.service.UserService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.utils.StringUtil;
import io.github.ygsama.oauth2server.web.UserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 杨光
 */
@Slf4j
@RestController
public class UserResourceImpl implements UserResource {

	private UserService userService;

	@Autowired
	UserResourceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	public BaseDTO addUser(UserDTO u) {
		BaseDTO dto;
		try {
			if (u.getUsername() == null || u.getPassword() == null || u.getName() == null ) {
				return new UserDTO(ResultEnum.PARAM_LACK);
			}
			dto = userService.addUser(u);
		} catch (Exception e) {
			dto = new UserDTO(ResultEnum.FAIL);
			log.error("[添加用户信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public BaseDTO updateUserByNo(UserDTO u) {
		BaseDTO dto;
		try {
			if (u.getUsername() == null || u.getName() == null ) {
				return new UserDTO(ResultEnum.PARAM_LACK);
			}
			dto = userService.updateUser(u);

		} catch (Exception e) {
			dto = new UserDTO(ResultEnum.FAIL);
			log.error("[修改用户信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public BaseDTO deleteUser(String username) {
		log.info("{}", username);
		BaseDTO dto;
		try {
			if (username == null || "".equals(username)) {
				return new UserDTO(ResultEnum.PARAM_LACK);
			}
			dto = userService.deleteByUsername(username);
		} catch (Exception e) {
			dto = new UserDTO(ResultEnum.FAIL);
			log.error("[删除用户信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public PageDTO getUserListByPage(Map<String, Object> params) {
		PageDTO dto;
		try {
			int curPage = StringUtil.objectToInt(params.get("curPage"));
			int pageSize = StringUtil.objectToInt(params.get("pageSize"));
			if (curPage < 1 || pageSize < 1) {
				return new PageDTO<>(ResultEnum.FAIL);
			}
			dto = userService.getUserList(params);
		} catch (Exception e) {
			dto = new PageDTO<>(ResultEnum.FAIL);
			log.error("[获取分页用户列表异常]: ", e);
		}
		return dto;
	}

	/**
	 * 登录后，查询当前登录用户详情
	 *
	 * @return UserDTO
	 */
	@Override
	public UserDTO getUserDetailByNo() {
		UserDTO dto;
		try {
			SysUserDO userDO = (SysUserDO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = userDO.getUsername();
			if (StringUtil.isNullorEmpty(username)) {
				return new UserDTO(ResultEnum.PARAM_LACK);
			}
			dto = userService.getUserDetailByUsername(username);
		} catch (Exception e) {
			dto = new UserDTO(ResultEnum.FAIL);
			log.error("[获取用户详情异常]: ", e);
		}
		return dto;
	}

	/**
	 * 修改密码
	 *
	 * @param dto password newPassword
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO modPassword(UserDTO dto) {
		if (StringUtil.isNullorEmpty(dto.getPassword()) || StringUtil.isNullorEmpty(dto.getNewPassword())) {
			return new UserDTO(ResultEnum.FAIL);
		}
		try {
			return userService.modPassword(dto);
		} catch (Exception e) {
			log.error("[修改密码异常]: ", e);
			return new BaseDTO(ResultEnum.FAIL);
		}
	}


	/**
	 * 重置密码
	 *
	 * @param dto username newPassword
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO resetPassword(UserDTO dto) {
		if (StringUtil.isNullorEmpty(dto.getUsername()) || StringUtil.isNullorEmpty(dto.getNewPassword())) {
			return new UserDTO(ResultEnum.FAIL);
		}
		try {
			return userService.resetPassword(dto);
		} catch (Exception e) {
			log.error("[重置密码异常]: ", e);
			return new BaseDTO(ResultEnum.FAIL);
		}
	}
}
