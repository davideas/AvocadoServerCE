package eu.davidea.avocadoserver.boundary.helpers;

import eu.davidea.avocadoserver.boundary.rest.api.menu.MenuDTO;
import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantDTO;
import eu.davidea.avocadoserver.business.menu.Menu;
import eu.davidea.avocadoserver.business.restaurant.Restaurant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Davide
 * @since 14/08/2016
 */
@Component
public class EntityToDtoHelper {

    public List<RestaurantDTO> toDtos(List<Restaurant> restaurants) {
        List<RestaurantDTO> restaurantDtoList = new ArrayList<>(restaurants.size());
        restaurantDtoList.addAll(restaurants.stream().map(this::toDto).collect(Collectors.toList()));
        return restaurantDtoList;
    }

    public RestaurantDTO toDto(Restaurant restaurant) {
        RestaurantDTO restaurantDto = new RestaurantDTO();

        restaurantDto.setId(restaurant.getId());
        restaurantDto.setUserId(restaurant.getUserId());
        restaurantDto.setCreDate(restaurant.getCreDate());
        restaurantDto.setModDate(restaurant.getModDate());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setTables(restaurant.getTables());
        restaurantDto.setPlaces(restaurant.getPlaces());
        restaurantDto.setLanguageCode(restaurant.getLanguageCode());
        restaurantDto.setCountryCode(restaurant.getCountryCode());
        restaurantDto.setCurrencyCode(restaurant.getCurrencyCode());
        restaurantDto.setLatitude(restaurant.getLatitude());
        restaurantDto.setLongitude(restaurant.getLongitude());
        restaurantDto.setDistance(restaurant.getDistance());
        restaurantDto.setDisplayAddress(restaurant.getDisplayAddress());
        restaurantDto.setDisplayZip(restaurant.getDisplayZip());
        restaurantDto.setDisplayCity(restaurant.getDisplayCity());
        restaurantDto.setDisplayProvince(restaurant.getDisplayProvince());
        restaurantDto.setDisplayEmail(restaurant.getDisplayEmail());
        restaurantDto.setDisplayPhone(restaurant.getDisplayPhone());
        restaurantDto.setWebsite(restaurant.getWebsite());
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setStatus(restaurant.getStatus());

        return restaurantDto;
    }

    public List<MenuDTO> toDto(List<Menu> menus) {
        return null;
    }

    public MenuDTO toDto(Menu menu) {
        return null;
    }
}