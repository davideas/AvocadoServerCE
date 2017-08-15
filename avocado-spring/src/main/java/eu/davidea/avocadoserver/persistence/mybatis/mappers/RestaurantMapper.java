package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * https://github.com/mybatis/mybatis-3/wiki/FAQ
 *
 * @author Davide
 * @since 07/08/2017
 */
@Mapper
public interface RestaurantMapper {

    @Select("select * from restaurants where lower(name) like #{name,jdbcType=VARCHAR} and status != 'DELETED' order by name asc")
    List<Restaurant> findRestaurantByName(String name);

    @Select("select * from restaurants where id = #{id}")
    Restaurant getRestaurantById(Long id);

}