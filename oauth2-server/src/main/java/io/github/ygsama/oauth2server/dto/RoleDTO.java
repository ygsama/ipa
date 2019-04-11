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
@ApiModel(value = "角色", description = "角色")
public class RoleDTO extends BaseDTO {

	private Integer no;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 类型
	 */
	private Integer catalog;

	/**
	 * 机构级别
	 */
	private Integer orgGradeNo;

	/**
	 * 描述
	 */
	private String note;

	private List<MenuDTO> menuList;

	public RoleDTO() {
	}

	public RoleDTO(ResultEnum re) {
		super(re);
	}

}
