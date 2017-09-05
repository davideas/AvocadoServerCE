package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.business.user.UserToken;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Davide
 * @since 27/08/2017
 */
@Mapper
public interface UserMapper {

    @Select("select * from users where id = #{id} and status != 'DELETED'")
    User findById(Long id);

    @Select("select * from users where username = #{username,jdbcType=VARCHAR} and status != 'DELETED'")
    User findByUsername(String username);

    @Select("select * from users where email = #{email,jdbcType=VARCHAR} and status != 'DELETED'")
    User findByEmail(String email);

    @Select("select * from users u inner join user_tokens ut on u.id = ut.user_id where jti = #{jti}")
    UserToken findByJti(String jti);

    @Select("select * from users u inner join user_tokens ut on u.id = ut.user_id where userId = #{userId}")
    List<UserToken> getUserTokens(Long userId);

    @Insert("insert into user_tokens (user_id, jti, enabled, os_name, os_version, user_agent, cre_date, exp_date, last_login_date) values (" +
            "#{userId}, #{jti,jdbcType=VARCHAR}, #{enabled}, #{osName,jdbcType=VARCHAR}, " +
            "#{osVersion,jdbcType=VARCHAR}, #{userAgent,jdbcType=VARCHAR}, " +
            "#{creDate,jdbcType=TIMESTAMP}, #{expDate,jdbcType=TIMESTAMP}, #{lastLoginDate,jdbcType=TIMESTAMP})")
    int insertUserToken(UserToken userToken);

    @Update("update user_tokens set last_login_date = current_timestamp() where jti = #{jti,jdbcType=VARCHAR}")
    int updateLastLoginDate(String jti);

    @Delete("delete from user_tokens where jti = #{jti,jdbcType=VARCHAR}")
    int deleteToken(String jti);

}