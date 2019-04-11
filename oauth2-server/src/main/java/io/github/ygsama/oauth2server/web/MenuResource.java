package io.github.ygsama.oauth2server.web;

import io.github.ygsama.oauth2server.dto.ListDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 杨光
 */
@Api(value = "权限模块：菜单管理", tags = {"权限模块：菜单管理"})
@RequestMapping("/auth/v2/menu")
public interface MenuResource {


	@GetMapping
	@ApiOperation(value = "查询所有菜单列表", notes = "查询所有菜单列表")
	ListDTO getAllMenuList();


}
