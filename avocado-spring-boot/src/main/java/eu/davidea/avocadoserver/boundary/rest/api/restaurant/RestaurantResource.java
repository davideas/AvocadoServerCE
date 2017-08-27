package eu.davidea.avocadoserver.boundary.rest.api.restaurant;

import eu.davidea.avocadoserver.business.enums.EnumUnitMeasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Davide
 * @since 06/08/2017
 */
@RestController
@RequestMapping(value = "api/v1/restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantResource {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantResource.class);
    private RestaurantFacade restaurantFacade;

    @Autowired
    public RestaurantResource(RestaurantFacade restaurantFacade) {
        this.restaurantFacade = restaurantFacade;
    }

    @GetMapping
    @RequestMapping()
    public ResponseEntity findRestaurantsNearby(@RequestParam(name = "name", required = false, value = "") String name,
                                                @RequestParam(name = "lat", required = false) Float latitude,
                                                @RequestParam(name = "long", required = false) Float longitude,
                                                @RequestParam(name = "radius", required = false) Short radius,
                                                @RequestParam(name = "unit", required = false) EnumUnitMeasure unit)
            throws MissingServletRequestParameterException {

        List<RestaurantDTO> restaurantDtoList;
        if (name != null) {
            logger.trace("findRestaurantByName(name={})", name);
            restaurantDtoList = restaurantFacade.findRestaurantByName(name);
        } else if (latitude == null && longitude == null && radius == null) {
            throw new MissingServletRequestParameterException("name", "String");
        } else if (latitude == null) {
            throw new MissingServletRequestParameterException("lat", "Float");
        } else if (longitude == null) {
            throw new MissingServletRequestParameterException("long", "Float");
        } else if (radius == null) {
            throw new MissingServletRequestParameterException("radius", "Short");
        } else {
            logger.trace("findRestaurantsNearby(lat={}, long={}, radius={}, unit={})", latitude, longitude, radius, unit);
            restaurantDtoList = restaurantFacade.findRestaurantsNearby(latitude, longitude, radius, unit);
        }

        return ResponseEntity.ok().body(restaurantDtoList);
    }

    @GetMapping
    @RequestMapping("/{restaurantId}")
    public ResponseEntity getRestaurantById(@PathVariable Long restaurantId) {
        logger.trace("getRestaurantById(restaurantId={})", restaurantId);

        RestaurantDTO restaurantDTO = restaurantFacade.getRestaurantById(restaurantId);
        return ResponseEntity.ok().body(restaurantDTO);
    }

}