package eu.davidea.avocadoserver.business.restaurant;

import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ObjectNotFoundException;
import eu.davidea.avocadoserver.infrastructure.statistics.StatsLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Service
public class GetRestaurantUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetRestaurantUseCase.class);

    private RestaurantRepository restaurantRepository;
    private StatsLogger statsLogger;

    @Autowired
    public GetRestaurantUseCase(RestaurantRepository restaurantRepository,
                                StatsLogger statsLogger) {
        this.restaurantRepository = restaurantRepository;
        this.statsLogger = statsLogger;
    }

    public List<Restaurant> findRestaurantsNearby(Float latitude, Float longitude, Float radius) {
        throw new NotImplementedException("findRestaurantsNearby");
    }

    public List<Restaurant> findRestaurantByName(String restaurantName) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("findRestaurantByName");

        try {
            List<Restaurant> restaurants = restaurantRepository.findRestaurantByName(restaurantName);
            if (restaurants.isEmpty()) {
                throw ObjectNotFoundException.noRestaurantFound(restaurantName);
            }
            logger.debug("Found {} Restaurants with name='{}'", restaurants.size(), restaurantName);
            return restaurants;

        } finally {
            stopWatch.stop();
            statsLogger.logQueryStats(stopWatch);
        }
    }

    public Restaurant getRestaurantById(Long restaurantId) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getRestaurantById");

        try {
            Restaurant restaurant = restaurantRepository.getRestaurantById(restaurantId);
            if (restaurant == null) {
                throw ObjectNotFoundException.noRestaurantFound(restaurantId);
            }
            return restaurant;

        } finally {
            stopWatch.stop();
            statsLogger.logQueryStats(stopWatch);
        }
    }

}