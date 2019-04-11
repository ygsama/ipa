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

	@Select("select USERNAME, PASSWORD, NAME, STATUS, ONLINE_FLAG, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO," +
			"LOGIN_IP, LOGIN_TIME,LOGIN_TERM, PASSWORD_EXPIRATION,PASSWORD_ERROR " +
			" from sys_user where username=#{username}")
	@Results({
			@Result(column = "USERNAME", property = "username", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PASSWORD", property = "password", jdbcType = JdbcType.VARCHAR),
			@Result(column = "NAME", property = "name", jdbcType = JdbcType.VARCHAR),
			@Result(column = "STATUS", property = "status", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ONLINE_FLAG", property = "onlineFlag", jdbcType = JdbcType.DECIMAL),
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.DECIMAL),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "com.zjft.microservice.authadmin.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "ORG_NO", property = "sysOrg", one = @One(select = "com.zjft.microservice.authadmin.repository.SysOrgMapper.selectByPrimaryKey")),
			@Result(column = "USERNAME", property = "menuList", one = @One(select = "com.zjft.microservice.authadmin.repository.SysMenuMapper.queryMenuByUsername"))
	})
	SysUserDO selectByPrimaryKey(String username);

	@Delete({
			"delete from SYS_USER_ROLE",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int deleteUserRole(String username);

	@Insert("<script> " +
			"insert into SYS_USER_ROLE(USERNAME,ROLE_NO) " +
			"<foreach item='item' index='index' collection='role' separator='union all' > " +
			"(SELECT #{username},#{item.no} from dual) " +
			"</foreach>" +
			"</script> "
	)
	void insertUserRole(@Param("username") String username, @Param("role") List<SysRoleDO> role);

	@Insert({
			"insert into SYS_USER (USERNAME, PASSWORD, NAME, ORG_NO, PHONE, MOBILE, EMAIL, PHOTO)",
			"values (#{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
			"#{name,jdbcType=VARCHAR}, #{orgNo,jdbcType=VARCHAR}, #{phone,jdbcType=VARCHAR}, ",
			"#{mobile,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{photo,jdbcType=VARCHAR})"
	})
	int insert(SysUserDO record);

	@Update({
			"update SYS_USER SET ",
			"NAME = #{name,jdbcType=VARCHAR},",
			"ORG_NO = #{orgNo,jdbcType=VARCHAR},",
			"PHONE = #{phone,jdbcType=VARCHAR},",
			"MOBILE = #{mobile,jdbcType=VARCHAR},",
			"EMAIL = #{email,jdbcType=VARCHAR},",
			"PHOTO = #{photo,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int updateByPrimaryKey(SysUserDO record);


	@Select("<script> " +
			"select a1.USERNAME, a1.PASSWORD, a1.NAME, a1.STATUS, a1.ONLINE_FLAG, a1.ORG_NO, a1.PHONE, a1.MOBILE, " +
			"a1.EMAIL, a1.PHOTO, a1.LOGIN_IP, a1.LOGIN_TIME,a1.LOGIN_TERM, a1.PASSWORD_EXPIRATION,a1.PASSWORD_ERROR " +
			"from (select SYS_USER.*,rownum rn from SYS_USER) a1 " +
			"where 1=1 " +
			"  and a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) " +
			"  <if test=\"orgNo != null and orgNo != '' \"> " +
			"    AND a1.ORG_NO = #{orgNo,jdbcType=VARCHAR} </if>" +
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
			@Result(column = "ORG_NO", property = "orgNo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHONE", property = "phone", jdbcType = JdbcType.VARCHAR),
			@Result(column = "MOBILE", property = "mobile", jdbcType = JdbcType.VARCHAR),
			@Result(column = "EMAIL", property = "email", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PHOTO", property = "photo", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_IP", property = "loginIp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TIME", property = "loginTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LOGIN_TERM", property = "loginTerm", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_EXPIRATION", property = "passwordExpiration", jdbcType = JdbcType.VARCHAR),
			@Result(column = "PASSWORD_ERROR", property = "passwordError", jdbcType = JdbcType.NUMERIC),
			@Result(column = "USERNAME", property = "roleList", many = @Many(select = "com.zjft.microservice.authadmin.repository.SysRoleMapper.qryUserRoleByUsername")),
			@Result(column = "ORG_NO", property = "sysOrg", one = @One(select = "com.zjft.microservice.authadmin.repository.SysOrgMapper.selectByPrimaryKey"))
	})
	List<SysUserDO> queryByPage(Map<String, Object> param);


	@Select("<script>" +
			"select count(*) from sys_user a1 " +
			"where 1=1 " +
			"  and a1.ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left &gt;= tOrg.LEFT and o.right &lt;= tOrg.RIGHT) " +
			"  <if test=\"orgNo != null and orgNo != '' \"> " +
			"    AND a1.ORG_NO = #{orgNo,jdbcType=VARCHAR} </if>" +
			"  <if test=\"roleNo != null and roleNo != '' \"> " +
			"    AND a1.USERNAME in ( select USERNAME from sys_user_role where ROLE_NO=#{roleNo,jdbcType=NUMERIC})</if>" +
			"  <if test=\"username != null and username != '' \"> " +
			"    AND a1.USERNAME like CONCAT('%',CONCAT(#{username,jdbcType=VARCHAR},'%')) </if>" +
			"  <if test=\"name != null and name != '' \"> " +
			"    AND a1.NAME like CONCAT('%',CONCAT(#{name,jdbcType=VARCHAR},'%')) </if>" +
			"</script>")
	int queryTotalRow(Map<String, Object> param);


	@Delete({
			"delete from SYS_USER",
			"where username = #{username,jdbcType=VARCHAR}"
	})
	int deleteByPrimaryKey(String username);


	@Update({
			"update SYS_USER SET ",
			"  PASSWORD = #{password,jdbcType=VARCHAR} ",
			"where USERNAME = #{username,jdbcType=VARCHAR}"
	})
	int modPassword(@Param("username") String username, @Param("password") String password);


	@Select("select count(1) from SYS_USER " +
			"where " +
			"    ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{userOrgNo,jdbcType=VARCHAR} where o.left > tOrg.LEFT and o.right < tOrg.RIGHT) " +
			"    and USERNAME =#{username,jdbcType=VARCHAR}"
	)
	int hasPermission(@Param("userOrgNo") String userOrgNo, @Param("username") String username);

}
