package io.github.ygsama.oauth2server.repository;

import io.github.ygsama.oauth2server.domain.SysRoleDO;
import io.github.ygsama.oauth2server.domain.SysUserDO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 杨光
 */
@Mapper
@Repository
public interface SysUserMapper {

	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, PHONE, MOBILE, EMAIL, PHOTO," +
			"LOGIN_IP, LOGIN_TIME,LOGIN_TERM, PASSWORD_EXPIRATION,PASSWORD_ERROR " +
			" from sys_user where username=#{username}")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "io.github.ygsama.oauth2server.repository.SysRoleMapper.qryUserRoleByUsername")),
	})
	SysUserDO selectByPrimaryKey(String username);


	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, PHONE, MOBILE, EMAIL, PHOTO," +
			"LOGIN_IP, LOGIN_TIME,LOGIN_TERM, PASSWORD_EXPIRATION,PASSWORD_ERROR " +
			" from sys_user where username=#{username}")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "io.github.ygsama.oauth2server.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "USERNAME", property = "menuList", many = @Many(select = "io.github.ygsama.oauth2server.repository.SysMenuMapper.queryMenuByUsername"))
	})
	SysUserDO selectByPrimaryKeyWithMenu(String username);

	@Delete({
			"delete from sys_user_role",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int deleteUserRole(String username);

	@Insert("<script> " +
			"insert into sys_user_role(USERNAME,ROLE_NO) " +
			"<foreach item='item' index='index' collection='role' separator='union all' > " +
			"(SELECT #{username},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	void insertUserRole(@Param("username") String username, @Param("role") List<SysRoleDO> role);

	@Insert({
			"insert into sys_user (USERNAME, PASSWORD, NAME, PHONE, MOBILE, EMAIL, PHOTO)",
			"values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
			"#{name,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
			"#{mobile,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR})"
	})
	int insert(SysUserDO record);

	@Update({
			"update sys_user SET ",
			"NAME = #{name,jdbcType=VARCHAR},",
			"PHONE = #{phone,jdbcType=VARCHAR},",
			"MOBILE = #{mobile,jdbcType=VARCHAR},",
			"EMAIL = #{email,jdbcType=VARCHAR},",
			"PHOTO = #{photo,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int updateByPrimaryKey(SysUserDO record);


	@Select("<script> " +
			"select a1.USERNAME, a1.PASSWORD, a1.NAME, a1.STATUS, a1.ONLINE_FLAG, a1.PHONE, a1.MOBILE, " +
			"a1.EMAIL, a1.PHOTO, a1.LOGIN_IP, a1.LOGIN_TIME,a1.LOGIN_TERM, a1.PASSWORD_EXPIRATION,a1.PASSWORD_ERROR " +
			"from (select sys_user.*,rownum rn from sys_user) a1 " +
			"where 1=1 " +
			"  <if test=\"roleNo != null and roleNo != '' \"> " +
			"    AND a1.USERNAME in ( select USERNAME from sys_user_role where ROLE_NO=#{roleNo,jdbcType=NUMERIC})</if>" +
			"  <if test=\"username != null and username != '' \"> " +
			"    AND a1.USERNAME like CONCAT('%',CONCAT(#{username,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"  and rn &gt; #{startRow,jdbcType=NUMERIC} and rn &lt;= #{endRow,jdbcType=NUMERIC} " +
			"</script>"
	)
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.NUMERIC),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.NUMERIC),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.NUMERIC),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "io.github.ygsama.oauth2server.repository.SysRoleMapper.qryUserRoleByUsername")),
	})
	List<SysUserDO> queryByPage(Map<String, Object> param);


	@Select("<script>" +
			"select count(*) from sys_user a1 " +
			"where 1=1 " +
			"  <if test=\"roleNo != null and roleNo != '' \"> " +
			"    AND a1.USERNAME in ( select USERNAME from sys_user_role where ROLE_NO=#{roleNo,jdbcType=NUMERIC})</if>" +
			"  <if test=\"username != null and username != '' \"> " +
			"    AND a1.USERNAME like CONCAT('%',CONCAT(#{username,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"</script>")
	int queryTotalRow(Map<String, Object> param);


	@Delete({
			"delete from sys_user",
			"where username = #{username,jdbcType=VARCHAR}"
	})
	int deleteByPrimaryKey(String username);


	@Update({
			"update sys_user SET ",
			"  PASSWORD = #{password,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int modPassword(@Param("username") String username, @Param("password") String password);


	@Select("select count(1) from sys_user " +
			"where " +
			"     USERNAME =#{username,jdbcType=VARCHAR}"
	)
	int hasPermission(@Param("username") String username);

}
