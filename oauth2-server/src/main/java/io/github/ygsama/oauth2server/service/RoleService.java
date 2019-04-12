package io.github.ygsama.oauth2server.service;


import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.RoleDTO;

import java.util.Map;

public interface RoleService {

	PageDTO<RoleDTO> getRoleListByPage(Map<String, Object> param);

	RoleDTO getRoleDetailByNo(String no);

	BaseDTO addRole(RoleDTO p);

	BaseDTO updateRole(RoleDTO p);

	BaseDTO deleteRoleByNo(int no);
}
