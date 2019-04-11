package io.github.ygsama.oauth2server.dto;

import io.github.ygsama.oauth2server.utils.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
@ApiModel(value = "权限", description = "权限")
public class PermissionDTO extends BaseDTO {

	@ApiModelProperty(value = "城市")
	private String no;

	@ApiModelProperty(value = "权限名称")
	private String name;

	@ApiModelProperty(value = "资源URL")
	private String url;

	@ApiModelProperty(value = "资源请求类型")
	private String method;

	@ApiModelProperty(value = "资源类型")
	private Integer catalog;

	@ApiModelProperty(value = "备注")
	private String note;

	public PermissionDTO() {
	}

	public PermissionDTO(ResultEnum re) {
		super(re);
	}

}
