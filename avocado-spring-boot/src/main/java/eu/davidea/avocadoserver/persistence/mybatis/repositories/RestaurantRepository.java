package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.audit.LogQueryStats;
import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("restaurantRepository")
public class RestaurantRepository {

    private final RestaurantMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    RestaurantRepository(RestaurantMapper mapper) {
        this.mapper = mapper;
    }

    public List<Restaurant> findRestaurantsNearby(Float latitude, Float longitude, short radius, short unit) {
        return mapper.findRestaurantsNearby(latitude, longitude, radius, unit);
    }

    @LogQueryStats
    public List<Restaurant> findRestaurantByName(String name) {
        name = "%" + name.toLowerCase() + "%";
        return mapper.findRestaurantByName(name);
    }

    @LogQueryStats
    public Restaurant getRestaurantById(Long id) {
        return mapper.getRestaurantById(id);
    }

}