package eu.davidea.avocadoserver.business.restaurant;

import org.springframework.util.StopWatch;

import java.util.List;

/**
 * @author Davide
 * @since 18/08/2016
 */
public interface RestaurantRepository {

    List<Restaurant> findRestaurantByName(String name);

    Restaurant getRestaurantById(Long id);

}