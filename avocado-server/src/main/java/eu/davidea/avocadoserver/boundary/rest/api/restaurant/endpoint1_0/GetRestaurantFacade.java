package eu.davidea.avocadoserver.boundary.rest.api.restaurant.endpoint1_0;

import eu.davidea.avocadoserver.boundary.helpers.EntityToDtoHelper;
import eu.davidea.avocadoserver.boundary.rest.api.restaurant.model.RestaurantDTO;
import eu.davidea.avocadoserver.business.restaurant.GetRestaurantUseCase;
import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Service
public class GetRestaurantFacade {

    private final static Logger logger = LoggerFactory.getLogger(GetRestaurantFacade.class);

    private GetRestaurantUseCase getRestaurantUseCase;
    private EntityToDtoHelper dtoHelper;

    @Autowired
    public GetRestaurantFacade(GetRestaurantUseCase getRestaurantUseCase, EntityToDtoHelper dtoHelper) {
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.dtoHelper = dtoHelper;
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> findRestaurantsNearby(Float latitude, Float longitude, Float radius) {
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);
        Objects.requireNonNull(radius);

        List<Restaurant> restaurants = getRestaurantUseCase.findRestaurantsNearby(latitude, longitude, radius);
        return dtoHelper.toDtos(restaurants);
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> findRestaurantByName(String name) {
        Objects.requireNonNull(name);

        List<Restaurant> restaurants = getRestaurantUseCase.findRestaurantByName(name);
        return dtoHelper.toDtos(restaurants);
    }

    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantById(Long id) {
        Objects.requireNonNull(id);

        Restaurant restaurant = getRestaurantUseCase.getRestaurantById(id);
        return dtoHelper.toDto(restaurant);
    }

}