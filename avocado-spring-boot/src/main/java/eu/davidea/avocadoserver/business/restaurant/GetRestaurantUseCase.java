package eu.davidea.avocadoserver.business.restaurant;

import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ObjectNotFoundException;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Service
public class GetRestaurantUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetRestaurantUseCase.class);

    private RestaurantRepository restaurantRepository;

    @Autowired
    public GetRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> findRestaurantsNearby(Float latitude, Float longitude, Float radius) {
        throw new NotImplementedException("findRestaurantsNearby");
    }

    public List<Restaurant> findRestaurantByName(String restaurantName) {
        Objects.requireNonNull(restaurantName);

        List<Restaurant> restaurants = restaurantRepository.findRestaurantByName(restaurantName);
        if (restaurants.isEmpty()) {
            throw ObjectNotFoundException.noRestaurantFound(restaurantName);
        }
        logger.debug("Found {} Restaurants with name='{}'", restaurants.size(), restaurantName);
        return restaurants;
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        Objects.requireNonNull(restaurantId);

        Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw ObjectNotFoundException.noRestaurantFound(restaurantId);
        }
        return restaurant;
    }

}