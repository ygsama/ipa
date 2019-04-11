package io.github.ygsama.oauth2server.web.impl;


import io.github.ygsama.oauth2server.dto.ListDTO;
import io.github.ygsama.oauth2server.service.MenuService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.github.ygsama.oauth2server.web.MenuResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨光
 */
@Slf4j
@RestController
public class MenuResourceImpl implements MenuResource {

	private final MenuService menuService;

	@Autowired
	public MenuResourceImpl(MenuService menuService) {
		this.menuService = menuService;
	}

	@Override
	public ListDTO getAllMenuList() {
		try {
			return menuService.getAllMenuList();
		} catch (Exception e) {
			return new ListDTO(ResultEnum.FAIL);
		}

	}

}
