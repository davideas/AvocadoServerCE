package eu.davidea.avocadoserver.business.restaurant;

import eu.davidea.avocadoserver.business.enums.EnumUnitMeasure;
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

    /**
     * Retrieves the first 20 restaurants nearby to the current user location identified by its latitude and longitude.
     * <p>This use case adopts the SQL formula as shown in this
     * <a href="https://developers.google.com/maps/solutions/store-locator/clothing-store-locator">Google
     * Store Locator example</a>.</p>
     *
     * @param latitude  the Earth latitude
     * @param longitude the Earth longitude
     * @param radius    the distance in km or miles depends by the unit of measure
     * @param unit      the unit of measure to apply into the calculation
     * @return the list of restaurants found min=0, max=20
     */
    public List<Restaurant> findRestaurantsNearby(Float latitude, Float longitude, short radius, EnumUnitMeasure unit) {
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