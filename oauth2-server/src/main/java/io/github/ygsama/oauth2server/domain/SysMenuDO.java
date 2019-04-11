package io.github.ygsama.oauth2server.domain;


import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class SysMenuDO {

    private String no;

    private String name;

    private String menuFather;

	private String url;

    private Integer menuLevel;

    private Integer menuOrder;

    private String note;

    private String menuIcon;

    private String menuSize;

    private String menuBg;
	/**
	 * 是否是按钮级别的菜单
	 */
	private String buttonTag;
	/**
	 * 按钮本质是后端接口
	 * permission_NO
	 */
	private String button;


    private static final long serialVersionUID = 1L;


}