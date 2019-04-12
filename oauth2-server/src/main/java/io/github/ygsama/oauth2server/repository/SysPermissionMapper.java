package io.github.ygsama.oauth2server.repository;

import io.github.ygsama.oauth2server.domain.SysPermissionDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper
@Repository
public interface SysPermissionMapper {

	@Select("select * from sys_permission")
	List<SysPermissionDO> queryAll();

	@Select("select p.URL,p.METHOD, group_concat(rm.ROLE_NO) ROLE_LIST " +
			"from sys_role_menu rm " +
			"left join sys_menu menu on menu.NO=rm.MENU_NO " +
			"left join sys_permission p on menu.BUTTON = p.NO " +
			"where p.URL is not null " +
			"group by p.URL,p.METHOD")
	@Results({
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "ROLE_LIST", property = "roleList", jdbcType = JdbcType.VARCHAR)
	})
	List<SysPermissionDO> queryRolePermission();


	@Select("select URL, METHOD from sys_permission " +
			"where CATALOG = 3 or CATALOG = 1")
	@Results({
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
	})
	List<SysPermissionDO> queryPublicPermission();


	@Select("select a1.* from " +
			"(select sys_permission.*,rownum rn from sys_permission) a1 " +
			"where rn between #{startRow} and #{endRow}")
	List<SysPermissionDO> queryByPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

	@Delete({
			"delete from sys_permission",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	int deleteByPrimaryKey(String no);

	@Insert({
			"insert into sys_permission (NO, NAME, URL, METHOD, CATALOG, NOTE)",
			"values (#{no,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
			"#{url,jdbcType=VARCHAR}, #{method,jdbcType=VARCHAR}, ",
			"#{catalog,jdbcType=DECIMAL}, #{note,jdbcType=VARCHAR})"
	})
	int insert(SysPermissionDO record);


	@Select({
			"select",
			"NO, NAME, URL, METHOD, CATALOG, NOTE",
			"from sys_permission",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "URL", property = "url", jdbcType = JdbcType.VARCHAR),
			@Result(column = "METHOD", property = "method", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	SysPermissionDO selectByPrimaryKey(String no);

	@Update({
			"update sys_permission",
			"set NAME = #{name,jdbcType=VARCHAR},",
			"URL = #{url,jdbcType=VARCHAR},",
			"METHOD = #{method,jdbcType=VARCHAR},",
			"CATALOG = #{catalog,jdbcType=DECIMAL},",
			"NOTE = #{note,jdbcType=VARCHAR}",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	int updateByPrimaryKey(SysPermissionDO record);


	@Select("select count(*) from sys_permission")
	int queryTotalRow();
}
