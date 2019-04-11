package io.github.ygsama.oauth2server.mapstruct;

import io.github.ygsama.oauth2server.domain.SysMenuDO;
import io.github.ygsama.oauth2server.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper
public interface SysMenuConverter {

	SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);


	@Mappings({
			@Mapping(source = "menuIcon", target = "icon"),
			@Mapping(source = "menuSize", target = "size"),
			@Mapping(source = "menuBg", target = "backgroundColor"),
	})
	MenuDTO domain2dto(SysMenuDO domain);

	@Mappings({
			@Mapping(source = "icon", target = "menuIcon"),
			@Mapping(source = "size", target = "menuSize"),
			@Mapping(source = "backgroundColor", target = "menuBg"),
	})
	SysMenuDO dto2do(MenuDTO domain);


	List<MenuDTO> domain2dto(List<SysMenuDO> domain);
	
	List<SysMenuDO> dto2do(List<MenuDTO> domain);

}

