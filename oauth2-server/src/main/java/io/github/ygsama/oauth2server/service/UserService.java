package io.github.ygsama.oauth2server.service;


import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.UserDTO;

import java.util.Map;

public interface UserService {

	UserDTO getUserDetailByUsername(String username);

	PageDTO<UserDTO> getUserList(Map<String, Object> params);

	BaseDTO addUser(UserDTO u);

	BaseDTO updateUser(UserDTO u);

	BaseDTO deleteByUsername(String username);

	BaseDTO modPassword(UserDTO u);

	BaseDTO resetPassword(UserDTO u);
}
