package io.github.ygsama.oauth2server.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 实现了 {@link UserDetails}接口
 * 用于构建存储在SecurityContextHolder的Authentication对象
 *
 * @author 杨光
 */
@Slf4j
@Data
public class SysUserDO implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String name;

	private Integer status;

	private Integer onlineFlag;

	private String phone;

	private String mobile;

	private String email;

	private String photo;

	private String loginIp;

	private String loginTime;

	private String loginTerm;

	private String passwordExpiration;

	private Integer passwordError;

	private List<SysRoleDO> roleList;

	private List<SysMenuDO> menuList;


	public SysUserDO() {
	}


	public SysUserDO(SysUserDO user, List<SysRoleDO> roles) {
		this.username = user.username;
		this.password = user.password;
		this.roleList = roles;
	}

	/**
	 * 装填用户的角色列表
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roleList == null || roleList.size() < 1) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		log.info("[原始用户角色列表装填]: ", roleList);
		StringBuilder roles = new StringBuilder();
		for (SysRoleDO role : roleList) {
			roles.append("ROLE_").append(role.getNo()).append(",");
		}
		log.info("[遍历并返回用户的角色列表]: {}", AuthorityUtils.commaSeparatedStringToAuthorityList(roles.substring(0, roles.length() - 1)));
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles.substring(0, roles.length() - 1));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
