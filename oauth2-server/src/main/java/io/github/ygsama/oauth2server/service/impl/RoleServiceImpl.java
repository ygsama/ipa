package io.github.ygsama.oauth2server.service.impl;

import io.github.ygsama.oauth2server.domain.SysMenuDO;
import io.github.ygsama.oauth2server.domain.SysRoleDO;
import io.github.ygsama.oauth2server.dto.*;
import io.github.ygsama.oauth2server.mapstruct.SysMenuConverter;
import io.github.ygsama.oauth2server.mapstruct.SysRoleConverter;
import io.github.ygsama.oauth2server.repository.SysMenuMapper;
import io.github.ygsama.oauth2server.repository.SysRoleMapper;
import io.github.ygsama.oauth2server.service.RoleService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 角色管理ServiceImpl
 *
 * @author 杨光
 * @since 2019-02-26
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	private SysRoleMapper sysRoleMapper;
	private SysMenuMapper sysMenuMapper;

	@Autowired
	RoleServiceImpl(SysRoleMapper sysRoleMapper, SysMenuMapper sysMenuMapper) {
		this.sysRoleMapper = sysRoleMapper;
		this.sysMenuMapper = sysMenuMapper;
	}

	@Override
	public ListDTO<RoleDTO> getRoleListByOrgNo(String orgGradeNo) {
		ListDTO<RoleDTO> dto = new ListDTO<>(ResultEnum.FAIL);
		try {
			List<SysRoleDO> doList = sysRoleMapper.queryByOrgNo(orgGradeNo);
			List<RoleDTO> dtoList = SysRoleConverter.INSTANCE.domain2dto(doList);
			dto.setRetList(dtoList);
			dto.setResult(ResultEnum.SUCCEED);
		} catch (Exception e) {
			dto = new ListDTO<>(ResultEnum.FAIL);
			log.error("[查询下属角色列表]: ", e);
		}
		return dto;
	}

	/**
	 * 查询角色分页列表
	 *
	 * @param param orgGradeNo、curPage、pageSize
	 * @return PageDTO
	 */
	@Override
	public PageDTO<RoleDTO> getRoleListByPage(Map<String, Object> param) {
		PageDTO<RoleDTO> pageDTO;
		try {
			pageDTO = new PageDTO<>(ResultEnum.SUCCEED);
			String orgGradeNo = "%" + StringUtil.parseString(param.get("orgGradeNo")) + "%";
			int curPage = StringUtil.objectToInt(param.get("curPage"));
			int pageSize = StringUtil.objectToInt(param.get("pageSize"));
			int startRow = pageSize * (curPage - 1);
			int endRow = pageSize * curPage;
			int totalRow = sysRoleMapper.queryTotalRow(orgGradeNo);
			List<SysRoleDO> doList = sysRoleMapper.queryByPage(startRow, endRow, orgGradeNo);
			List<RoleDTO> dtoList = SysRoleConverter.INSTANCE.domain2dto(doList);
			pageDTO.setCurPage(curPage);
			pageDTO.setPageSize(pageSize);
			pageDTO.setTotalPage(totalRow / pageSize + 1);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setRetList(dtoList);
		} catch (Exception e) {
			pageDTO = new PageDTO<>(ResultEnum.FAIL);
			log.error("[查询角色分页列表]: ", e);
		}
		return pageDTO;
	}

	/**
	 * 查询角色信息，查询角色的menuList，查询角色拥有的buttonList，进行组合
	 *
	 * @param no 角色no
	 * @return RoleDTO
	 */
	@Override
	public RoleDTO getRoleDetailByNo(String no) {
		RoleDTO roleDTO;
		try {
			SysRoleDO roleDO = sysRoleMapper.queryRoleDetailByNo(no);
			List<SysMenuDO> sysMenuList = sysMenuMapper.queryAllMenu();
			List<SysMenuDO> buttonList = sysRoleMapper.queryMenuButtonByNo(no);
			roleDTO = SysRoleConverter.INSTANCE.domain2dto(roleDO);
			List<MenuDTO> list = SysMenuConverter.INSTANCE.domain2dto(sysMenuList);
			for (MenuDTO menu : list) {
				for (SysMenuDO button : buttonList) {
					if (menu.getNo().equals(button.getNo())) {
						menu.setChecked("1");
					}
				}
			}
			roleDTO.setMenuList(list);
		} catch (Exception e) {
			roleDTO = new RoleDTO(ResultEnum.FAIL);
			log.error("[查询角色详情]: ", e);
		}
		return roleDTO;
	}

	/**
	 * 添加角色，并添加角色菜单
	 *
	 * @param role RoleDTO
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO addRole(RoleDTO role) {
		try {
			int no = sysRoleMapper.selectMaxNo();
			SysRoleDO rDO = new SysRoleDO();
			rDO.setName(role.getName());
			rDO.setOrgGradeNo(role.getOrgGradeNo());
			rDO.setNote(role.getNote());
			rDO.setNo(no);

			if (sysRoleMapper.insert(rDO) == 1) {
				List<MenuDTO> menuList = role.getMenuList();
				if (menuList.size() > 0) {
					List<SysMenuDO> doList = SysMenuConverter.INSTANCE.dto2do(menuList);
					sysRoleMapper.insertRoleMenu(no, doList);
				}
				return new BaseDTO(ResultEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[插入角色信息异常]: ", e);
		}
		return new BaseDTO(ResultEnum.FAIL);
	}

	/**
	 * 更新角色，删除角色菜单后重新插入
	 *
	 * @param role RoleDTO
	 * @return BaseDTO
	 */
	@Override
	public BaseDTO updateRole(RoleDTO role) {
		try {
			SysRoleDO rDO = new SysRoleDO();
			rDO.setNo(role.getNo());
			rDO.setName(role.getName());
			rDO.setOrgGradeNo(role.getOrgGradeNo());
			rDO.setNote(role.getNote());
			if (sysRoleMapper.updateByPrimaryKey(rDO) == 1) {
				List<MenuDTO> menuList = role.getMenuList();
				if (menuList.size() > 0) {
					List<SysMenuDO> doList = SysMenuConverter.INSTANCE.dto2do(menuList);
					sysRoleMapper.deleteRoleMenu(role.getNo());
					sysRoleMapper.insertRoleMenu(role.getNo(), doList);
				}
				return new BaseDTO(ResultEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[修改角色信息异常]: ", e);
		}
		return new BaseDTO(ResultEnum.FAIL);
	}

	@Override
	public BaseDTO deleteRoleByNo(int no) {
		try {
			sysRoleMapper.deleteRoleMenu(no);
			int i = sysRoleMapper.deleteByPrimaryKey(no);
			if (i == 1) {
				return new BaseDTO(ResultEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[删除角色信息异常]: ", e);
		}
		return new BaseDTO(ResultEnum.FAIL);
	}
}
