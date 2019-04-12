package io.github.ygsama.oauth2server.repository;


import io.github.ygsama.oauth2server.domain.SysMenuDO;
import io.github.ygsama.oauth2server.domain.SysRoleDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 杨光
 */
@Repository
@Mapper
public interface SysRoleMapper {

	@Select("select t1.NO, t1.NAME, t1.CATALOG, t1.NOTE " +
			"from sys_role t1 " +
			"left join sys_user_role t2 on t1.no=t2.role_no " +
			"where t2.username=#{username}")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> qryUserRoleByUsername(String username);

	@Select("select a1.* from " +
			"(select sys_role.*,rownum rn from sys_role ) a1 " +
			"where rn > #{startRow} and rn <= #{endRow}")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> queryByPage(@Param("startRow") int startRow, @Param("endRow") int endRow);


	@Select("select NO, NAME, CATALOG, NOTE from sys_role ")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.DECIMAL),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	List<SysRoleDO> queryByOrgNo();


	@Select("select NO from sys_role")
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
	})
	List<String> queryAllRoleNo();


	@Select({
			"select",
			"NO, NAME, CATALOG, NOTE",
			"from sys_role",
			"where NO = #{no,jdbcType=VARCHAR}"
	})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.NUMERIC, id = true),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CATALOG", property = "catalog", jdbcType = JdbcType.NUMERIC),
			@Result(column = "NOTE", property = "note", jdbcType = JdbcType.VARCHAR)
	})
	SysRoleDO queryRoleDetailByNo(String no);

	@Select({
			"select MENU_NO no " +
					"from sys_role_menu " +
					"where ROLE_NO=#{no}"
	})
	List<SysMenuDO> queryMenuButtonByNo(String no);


	@Select("select count(*) from sys_role ")
	int queryTotalRow();


	@Delete({
			"delete from sys_role",
			"where NO = #{no,jdbcType=DECIMAL}"
	})
	int deleteByPrimaryKey(int no);

	@Delete({
			"delete from sys_role_menu",
			"where ROLE_NO = #{roleNo,jdbcType=DECIMAL}"
	})
	int deleteRoleMenu(int roleNo);

	@Insert({
			"insert into sys_role (NO, NAME, NOTE)",
			"values (#{no,jdbcType=NUMERIC}, #{name,jdbcType=VARCHAR}, #{note,jdbcType=VARCHAR})"
	})
	int insert(SysRoleDO record);

	@Insert("<script> " +
			"insert into sys_role_MENU(ROLE_NO,MENU_NO) " +
			"<foreach item='item' index='index' collection='menu' separator='union all' > " +
			"(SELECT #{roleNo},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	int insertRoleMenu(@Param("roleNo") Integer roleNo, @Param("menu") List<SysMenuDO> menu);

	@Select("select t1.* from sys_role t1 " +
			"left join sys_role_permission t2 on t1.no=t2.ROLE_NO " +
			"where t2.PERMISSION_NO=#{no}")
	List<SysRoleDO> selectByPermissionNo(String no);


	@Select("select max(no)+1 from sys_role ")
	int selectMaxNo();

	@Update({
			"update sys_role ",
			"set NAME = #{name,jdbcType=VARCHAR}, ",
			"NOTE = #{note,jdbcType=VARCHAR} ",
			"where NO = #{no,jdbcType=NUMERIC} "
	})
	int updateByPrimaryKey(SysRoleDO record);
}
