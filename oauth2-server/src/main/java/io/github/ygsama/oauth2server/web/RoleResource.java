package io.github.ygsama.oauth2server.web;

import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.ListDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.RoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author 杨光
 */
@Api(value = "权限模块：角色管理", tags = {"权限模块：角色管理"})
@RequestMapping("/auth/v2/role")
public interface RoleResource {

	/**
	 * 添加角色及菜单
	 *
	 * @param role 名称 描述 机构级别 按钮列表
	 * @return BaseDTO
	 */
	@PostMapping
	@ApiOperation(value = "添加角色", notes = "添加角色")
	BaseDTO addRole(@RequestBody RoleDTO role);


	/**
	 * 修改角色及菜单
	 *
	 * @param role 名称 描述 机构级别 按钮列表
	 * @return BaseDTO
	 */
	@PutMapping
	@ApiOperation(value = "修改角色", notes = "修改角色")
	BaseDTO updateRoleByNo(@RequestBody RoleDTO role);


	@DeleteMapping("/{no}")
	@ApiOperation(value = "删除角色", notes = "删除角色")
	@ApiImplicitParam(name = "no", value = "角色编号", paramType = "path")
	BaseDTO deleteRole(@PathVariable Integer no);


	@GetMapping("/{no}")
	@ApiOperation(value = "查询角色详情", notes = "查询角色详情")
	RoleDTO getDetailByRole(@PathVariable String no);


	@GetMapping
	@ApiOperation(value = "获取分页角色列表", notes = "获取分页角色列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgGradeNo", value = "机构等级", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO getRoleListByPage(@ApiIgnore @RequestParam Map<String, Object> param);


	@GetMapping("/grade")
	@ApiOperation(value = "查询该机构级别及下属级别角色列表", notes = "查询该机构级别及下属级别角色列表")
	@ApiImplicitParam(name = "orgGradeNo", value = "机构等级编号", paramType = "query")
	ListDTO getRoleListByOrgNo(@ApiIgnore @RequestParam String orgGradeNo);

}
