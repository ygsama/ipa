package io.github.ygsama.oauth2server.mapstruct;

import io.github.ygsama.oauth2server.domain.SysRoleDO;
import io.github.ygsama.oauth2server.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper
public interface SysRoleConverter {

	SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);


	@Mappings({})
	RoleDTO domain2dto(SysRoleDO domain);

	@Mappings({})
	SysRoleDO dto2do(RoleDTO domain);

	List<SysRoleDO> dto2do(List<RoleDTO> domain);

	List<RoleDTO> domain2dto(List<SysRoleDO> domain);
}

