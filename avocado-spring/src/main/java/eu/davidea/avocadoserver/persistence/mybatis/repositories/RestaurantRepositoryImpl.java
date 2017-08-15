package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import eu.davidea.avocadoserver.business.restaurant.RestaurantRepository;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("restaurantRepository")
class RestaurantRepositoryImpl implements RestaurantRepository {

    private final RestaurantMapper mapper;

    @Autowired
    RestaurantRepositoryImpl(RestaurantMapper mapper) {
        this.mapper = mapper;
    }

    public List<Restaurant> findRestaurantByName(String name) {
        name = "%" + name.toLowerCase() + "%";
        return mapper.findRestaurantByName(name);
    }

    public Restaurant getRestaurantById(Long id) {
        return mapper.getRestaurantById(id);
    }

}