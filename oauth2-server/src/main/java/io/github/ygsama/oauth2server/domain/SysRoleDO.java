package io.github.ygsama.oauth2server.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class SysRoleDO {

	private Integer no;

	private String name;

	private Integer catalog;

	private Integer orgGradeNo;

	private String note;

	private static final long serialVersionUID = 1L;

	public SysRoleDO() {
	}

	public SysRoleDO(Integer no, String name) {
		this.no = no;
		this.name = name;
	}
}
