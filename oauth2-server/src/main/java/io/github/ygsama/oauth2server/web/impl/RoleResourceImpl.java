package io.github.ygsama.oauth2server.web.impl;

import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.RoleDTO;
import io.github.ygsama.oauth2server.service.RoleService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.utils.StringUtil;
import io.github.ygsama.oauth2server.web.RoleResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author 杨光
 */
@Slf4j
@RestController
public class RoleResourceImpl implements RoleResource {

	private RoleService roleService;

	@Autowired
	RoleResourceImpl(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	public BaseDTO addRole(RoleDTO r) {
		BaseDTO dto;
		try {
			if (r.getName() == null || r.getOrgGradeNo() == null) {
				return new RoleDTO(ResultEnum.PARAM_LACK);
			}
			dto = roleService.addRole(r);
		} catch (Exception e) {
			dto = new RoleDTO(ResultEnum.FAIL);
			log.error("[添加角色信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public BaseDTO updateRoleByNo(RoleDTO r) {
		BaseDTO dto;
		try {
			if (r.getNo() == null || r.getName() == null || r.getOrgGradeNo() == null) {
				return new RoleDTO(ResultEnum.PARAM_LACK);
			}
			dto = roleService.updateRole(r);

		} catch (Exception e) {
			dto = new RoleDTO(ResultEnum.FAIL);
			log.error("[修改权限信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public BaseDTO deleteRole(Integer no) {
		BaseDTO dto;
		try {
			if (no == null || 0 == no) {
				return new RoleDTO(ResultEnum.PARAM_LACK);
			}
			dto = roleService.deleteRoleByNo(no);
		} catch (Exception e) {
			dto = new RoleDTO(ResultEnum.FAIL);
			log.error("[删除角色信息异常]: ", e);
		}
		return dto;
	}

	@Override
	public PageDTO getRoleListByPage(Map<String, Object> param) {
		PageDTO<RoleDTO> dto;
		try {
			int curPage = StringUtil.objectToInt(param.get("curPage"));
			int pageSize = StringUtil.objectToInt(param.get("pageSize"));
			if (curPage < 1 || pageSize < 1) {
				return new PageDTO<>(ResultEnum.FAIL);
			}
			dto = roleService.getRoleListByPage(param);
		} catch (Exception e) {
			dto = new PageDTO<>(ResultEnum.FAIL);
			log.error("[获取分页角色列表异常]: ", e);

		}
		return dto;
	}


	@Override
	public RoleDTO getDetailByRole(String no) {
		RoleDTO roleDTO;
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return new RoleDTO(ResultEnum.FAIL);
			}
			roleDTO = roleService.getRoleDetailByNo(no);
			roleDTO.setResult(ResultEnum.SUCCEED);

		} catch (Exception e) {
			log.error("[查询角色详情失败]:", e);
			return new RoleDTO(ResultEnum.FAIL);
		}
		return roleDTO;
	}
}
