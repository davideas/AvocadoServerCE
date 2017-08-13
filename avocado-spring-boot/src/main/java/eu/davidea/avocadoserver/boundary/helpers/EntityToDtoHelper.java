package eu.davidea.avocadoserver.boundary.helpers;

import eu.davidea.avocadoserver.boundary.rest.api.restaurant.model.RestaurantDTO;
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
        restaurantDto.setCreDate(restaurant.getCreDate());
        restaurantDto.setModDate(restaurant.getModDate());
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setLastLoginDate(restaurant.getLastLoginDate());
        restaurantDto.setTables(restaurant.getTables());
        restaurantDto.setPlaces(restaurant.getPlaces());
        restaurantDto.setLanguageCode(restaurant.getLanguageCode());
        restaurantDto.setCountryCode(restaurant.getCountryCode());
        restaurantDto.setCurrencyCode(restaurant.getCurrencyCode());
        restaurantDto.setLatitude(restaurant.getLatitude());
        restaurantDto.setLongitude(restaurant.getLongitude());
        restaurantDto.setAddress(restaurant.getAddress());
        restaurantDto.setZip(restaurant.getZip());
        restaurantDto.setCity(restaurant.getCity());
        restaurantDto.setProvince(restaurant.getProvince());
        restaurantDto.setEmail(restaurant.getEmail());
        restaurantDto.setPhone(restaurant.getPhone());
        restaurantDto.setWebsite(restaurant.getWebsite());
        restaurantDto.setOpenHours(restaurant.getOpenHours());
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setStatus(restaurant.getStatus());

        return restaurantDto;
    }

}