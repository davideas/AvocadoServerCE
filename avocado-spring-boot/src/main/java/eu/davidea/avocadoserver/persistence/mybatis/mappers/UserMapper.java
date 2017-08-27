package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}