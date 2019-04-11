package io.github.ygsama.oauth2server.repository;

import io.github.ygsama.oauth2server.domain.SysMenuDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper
@Repository
public interface SysMenuMapper {

	@Select({
			"select",
			"NO, NAME, MENU_FATHER, URL, MENU_LEVEL, MENU_ORDER, NOTE",
			"from SYS_MENU",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	SysMenuDO selectByPrimaryKey(String no);

	@Select({"select NO, NAME, MENU_FATHER,URL, MENU_LEVEL, MENU_ORDER, NOTE,MENU_ICON,MENU_SIZE,MENU_BG,BUTTON_TAG,BUTTON from SYS_MENU"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR)
	})
	List<SysMenuDO> queryAllMenu();


	@Select("select distinct m1.NO,m1.NAME,m1.MENU_FATHER,m1.URL, m1.MENU_LEVEL, m1.MENU_ORDER, m1.NOTE,m1.MENU_ICON,m1.MENU_SIZE,m1.MENU_BG,m1.BUTTON_TAG,m1.BUTTON " +
			"from SYS_MENU  m1 " +
			"left join SYS_ROLE_MENU on m1.NO = SYS_ROLE_MENU.MENU_NO " +
			"left join SYS_USER_ROLE on SYS_USER_ROLE.ROLE_NO = SYS_ROLE_MENU.ROLE_NO " +
			"start with SYS_USER_ROLE.USERNAME= #{username,jdbcType=VARCHAR} connect by prior m1.MENU_FATHER=m1.NO "
	)
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_FATHER", property = "menuFather", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_LEVEL", property = "menuLevel", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_ORDER", property = "menuOrder", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_ICON", property = "menuIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MENU_SIZE", property = "menuSize", jdbcType = JdbcType.NUMERIC),
			@Result(column = "MENU_BG", property = "menuBg", jdbcType = JdbcType.VARCHAR),
			@Result(column = "BUTTON_TAG", property = "buttonTag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "BUTTON", property = "button", jdbcType = JdbcType.VARCHAR)
	})
	List<SysMenuDO> queryMenuByUsername(String username);

}