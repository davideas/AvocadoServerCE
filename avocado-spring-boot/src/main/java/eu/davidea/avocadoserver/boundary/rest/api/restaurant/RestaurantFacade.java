package eu.davidea.avocadoserver.boundary.rest.api.restaurant;

import eu.davidea.avocadoserver.boundary.rest.api.menu.MenuDTO;
import eu.davidea.avocadoserver.business.enums.EnumUnitMeasure;
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

    @Autowired
    public RestaurantFacade(GetRestaurantUseCase getRestaurantUseCase, GetMenuUseCase getMenuUseCase) {
        this.getRestaurantUseCase = getRestaurantUseCase;
        this.getMenuUseCase = getMenuUseCase;
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> findRestaurantsNearby(Float latitude, Float longitude, Short radius, EnumUnitMeasure unit) {
        Objects.requireNonNull(latitude);
        Objects.requireNonNull(longitude);
        Objects.requireNonNull(radius);
        if (unit == null) {
            unit = EnumUnitMeasure.KM;
        }

        List<Restaurant> restaurants = getRestaurantUseCase.findRestaurantsNearby(latitude, longitude, radius, unit);
        return RestaurantDTO.toDtos(restaurants);
    }

    @Transactional(readOnly = true)
    public List<RestaurantDTO> findRestaurantByName(String name) {
        Objects.requireNonNull(name);

        List<Restaurant> restaurants = getRestaurantUseCase.findRestaurantByName(name);
        return RestaurantDTO.toDtos(restaurants);
    }

    @Transactional(readOnly = true)
    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Objects.requireNonNull(restaurantId);

        Restaurant restaurant = getRestaurantUseCase.getRestaurantById(restaurantId);
        return RestaurantDTO.toDto(restaurant);
    }

    @Transactional(readOnly = true)
    public List<MenuDTO> getMenus(Long restaurantId, String languageCode) {
        Objects.requireNonNull(restaurantId);
        if (languageCode == null) {
            languageCode = "en-UK";
        }
        List<Menu> menus = getMenuUseCase.getMenus(restaurantId, languageCode);
        return MenuDTO.toDto(menus);
    }
}