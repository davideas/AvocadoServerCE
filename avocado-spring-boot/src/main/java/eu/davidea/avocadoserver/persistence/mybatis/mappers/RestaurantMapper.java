package eu.davidea.avocadoserver.persistence.mybatis.mappers;

import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * https://github.com/mybatis/mybatis-3/wiki/FAQ
 *
 * @author Davide
 * @since 07/08/2017
 */
@Mapper
@CacheNamespace(flushInterval = 60000 * 60) // 60 minutes
public interface RestaurantMapper {

    /**
     * Use unit = 6371 for km, 3959 for miles.
     * https://developers.google.com/maps/solutions/store-locator/clothing-store-locator
     */
    @Select("SELECT r.*, ( #{unit} * acos( cos( radians(37) ) * cos( radians( #{latitude} ) ) " +
            "* cos( radians( #{longitude} ) - radians(-122) ) + sin( radians(37) )" +
            "* sin( radians( #{latitude} ) ) ) ) AS distance " +
            "FROM restaurants r WHERE status != 'DELETED' " +
            "HAVING distance <= #{radius} ORDER BY distance LIMIT 0, 20")
    List<Restaurant> findRestaurantsNearby(@Param("latitude") Float latitude,
                                           @Param("longitude") Float longitude,
                                           @Param("radius") short radius,
                                           @Param("unit") short unit);

    @Select("select * from restaurants where lower(name) like #{name,jdbcType=VARCHAR} and status != 'DELETED' order by name asc LIMIT 0, 20")
    List<Restaurant> findRestaurantByName(String name);

    @Select("select * from restaurants where id = #{id}")
    Restaurant getRestaurantById(Long id);

}