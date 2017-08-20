package eu.davidea.avocadoserver.boundary.rest.api.restaurant.endpoint1_0;

import eu.davidea.avocadoserver.boundary.helpers.EntityToDtoHelper;
import eu.davidea.avocadoserver.boundary.rest.api.menu.MenuDTO;
import eu.davidea.avocadoserver.boundary.rest.api.restaurant.model.RestaurantDTO;
import eu.davidea.avocadoserver.business.menu.GetMenuUseCase;
import eu.davidea.avocadoserver.business.menu.Menu;
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
public class RestaurantFacade {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantFacade.class);

    private GetRestaurantUseCase getRestaurantUseCase;
    private GetMenuUseCase getMenuUseCase;
    private EntityToDtoHelper dtoHelper;

    @Autowired
    public RestaurantFacade(GetRestaurantUseCase getRestaurantUseCase,
                            GetMenuUseCase getMenuUseCase, EntityToDtoHelper dtoHelper) {
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.getMenuUseCase = getMenuUseCase;
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
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Objects.requireNonNull(restaurantId);

        Restaurant restaurant = getRestaurantUseCase.getRestaurantById(restaurantId);
        return dtoHelper.toDto(restaurant);
    }

    @Transactional(readOnly = true)
    public List<MenuDTO> getMenus(Long restaurantId, String languageCode) {
        Objects.requireNonNull(restaurantId);
        if (languageCode == null) {
            languageCode = "en-UK";
        }

        List<Menu> menus = getMenuUseCase.getMenus(restaurantId, languageCode);
        return dtoHelper.toDto(menus);

    }
}