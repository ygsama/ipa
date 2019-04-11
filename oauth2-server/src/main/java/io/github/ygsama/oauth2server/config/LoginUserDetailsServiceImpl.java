package io.github.ygsama.oauth2server.config;

import io.github.ygsama.oauth2server.domain.SysRoleDO;
import io.github.ygsama.oauth2server.domain.SysUserDO;
import io.github.ygsama.oauth2server.repository.SysRoleMapper;
import io.github.ygsama.oauth2server.repository.SysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户登录的service实现类 <br>
 * 默认实现{@link JdbcDaoImpl} <br>
 *
 * @author 杨光
 */
@Slf4j
@Service("loginUserDetailsService")
public class LoginUserDetailsServiceImpl implements UserDetailsService {

	private final SysUserMapper sysUserMapper;
	private final SysRoleMapper sysRoleMapper;

	@Autowired
	public LoginUserDetailsServiceImpl(SysUserMapper sysUserMapper, SysRoleMapper sysRoleMapper) {
		this.sysUserMapper = sysUserMapper;
		this.sysRoleMapper = sysRoleMapper;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUserDO sysUserDO = sysUserMapper.selectByPrimaryKey(username);
		if (sysUserDO == null) {
			throw new UsernameNotFoundException(username);
		}
		List<SysRoleDO> sysRoles = sysRoleMapper.qryUserRoleByUsername(username);
		sysUserDO.setRoleList(sysRoles);

		log.info("[loadUserByUsername]: {}", sysUserDO);
		return sysUserDO;
	}
}
