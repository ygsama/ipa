package io.github.ygsama.oauth2server.service.impl;

import io.github.ygsama.oauth2server.domain.SysMenuDO;
import io.github.ygsama.oauth2server.dto.ListDTO;
import io.github.ygsama.oauth2server.dto.MenuDTO;
import io.github.ygsama.oauth2server.mapstruct.SysMenuConverter;
import io.github.ygsama.oauth2server.repository.SysMenuMapper;
import io.github.ygsama.oauth2server.service.MenuService;
import io.github.ygsama.oauth2server.utils.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 杨光
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

	private final SysMenuMapper sysMenuMapper;

	@Autowired
	public MenuServiceImpl(SysMenuMapper sysMenuMapper) {
		this.sysMenuMapper = sysMenuMapper;
	}

	@Override
	public ListDTO getAllMenuList() {
		ListDTO<MenuDTO> dto = new ListDTO<>(ResultEnum.FAIL);
		try {
			List<SysMenuDO> menuLsit = sysMenuMapper.queryAllMenu();
			List<MenuDTO> list = SysMenuConverter.INSTANCE.domain2dto(menuLsit);
			dto.setRetList(list);
			dto.setResult(ResultEnum.SUCCEED);
		} catch (Exception e) {
			log.error("getAllMenuList exception", e);
			dto.setResult(ResultEnum.FAIL);
		}
		return dto;
	}


}
