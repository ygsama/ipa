package io.github.ygsama.oauth2server.dto;

import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "用户", description = "用户")
public class UserDTO extends BaseDTO {
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String newPassword;

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

	private List<RoleDTO> roleList;


	private List<MenuDTO> menuList;


	public UserDTO() {
	}

	public UserDTO(ResultEnum re) {
		super(re);
	}

}
