package io.github.ygsama.oauth2server.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class SysPermissionDO {

	private String no;

	/**
	 * 权限名称
	 */
	private String name;

	/**
	 * URL
	 */
	private String url;

	/**
	 * 资源请求类型
	 */
	private String method;

	/**
	 * 类型
	 */
	private Integer catalog;

	/**
	 * 描述
	 */
	private String note;

	/**
	 * 角色列表
	 */
	private String roleList;

}
