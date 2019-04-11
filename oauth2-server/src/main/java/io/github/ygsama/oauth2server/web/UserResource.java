package io.github.ygsama.oauth2server.web;

import io.github.ygsama.oauth2server.dto.BaseDTO;
import io.github.ygsama.oauth2server.dto.PageDTO;
import io.github.ygsama.oauth2server.dto.UserDTO;
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
@Api(value = "权限模块：用户管理", tags = {"权限模块：用户管理"})
@RequestMapping("/auth/v2/user")
public interface UserResource {

	@PostMapping
	@ApiOperation(value = "添加用户", notes = "添加用户")
	BaseDTO addUser(@RequestBody UserDTO u);


	@PutMapping
	@ApiOperation(value = "修改用户", notes = "修改用户")
	BaseDTO updateUserByNo(@RequestBody UserDTO u);


	@DeleteMapping("/{username}")
	@ApiOperation(value = "删除用户", notes = "删除用户")
	@ApiImplicitParam(name = "username", value = "用户名", paramType = "path")
	BaseDTO deleteUser(@PathVariable("username") String username);


	@GetMapping
	@ApiOperation(value = "查询分页用户列表", notes = "查询分页用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNo", value = "机构编号", paramType = "query"),
			@ApiImplicitParam(name = "roleNo", value = "角色编号", paramType = "query"),
			@ApiImplicitParam(name = "username", value = "用户名", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "姓名", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页面", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "页面大小", required = true, paramType = "query"),
	})
	PageDTO getUserListByPage(@ApiIgnore @RequestParam Map<String, Object> params);


	@GetMapping("/detail")
	@ApiOperation(value = "查询当前登录用户详情", notes = "查询当前登录用户详情")
	UserDTO getUserDetailByNo();


	@PostMapping("/password")
	@ApiOperation(value = "用户自己修改密码", notes = "用户自己修改密码")
	BaseDTO modPassword(@RequestBody UserDTO u);


	@PutMapping("/password")
	@ApiOperation(value = "管理员重置密码", notes = "username、newPassword")
	BaseDTO resetPassword(@RequestBody UserDTO u);

}
