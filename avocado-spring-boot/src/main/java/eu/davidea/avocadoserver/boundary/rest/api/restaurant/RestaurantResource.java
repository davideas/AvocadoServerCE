package eu.davidea.avocadoserver.boundary.rest.api.restaurant;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUnitMeasure;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.PreAuthorize;
import eu.davidea.avocadoserver.infrastructure.security.RequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    @RequestMapping("/findByName")
    public ResponseEntity findRestaurantsByName(@RequestAttribute(RequestAttributes.TOKEN_REQ_ATTR) JwtToken jwtToken,
                                                @RequestParam(name = "name") String name) {

        logger.trace("findRestaurantByName(name={})", name);
        List<RestaurantDTO> restaurantDtoList = restaurantFacade.findRestaurantByName(name);
        return ResponseEntity.ok().body(restaurantDtoList);
    }

    @GetMapping
    @RequestMapping("/findNearby")
    public ResponseEntity findRestaurantsNearby(@RequestAttribute(RequestAttributes.TOKEN_REQ_ATTR) JwtToken jwtToken,
                                                @RequestParam(name = "lat") Float latitude,
                                                @RequestParam(name = "long") Float longitude,
                                                @RequestParam(name = "radius") Short radius,
                                                @RequestParam(name = "unit") EnumUnitMeasure unit) {

        logger.trace("findRestaurantsNearby(lat={}, long={}, radius={}, unit={})", latitude, longitude, radius, unit);
        List<RestaurantDTO> restaurantDtoList =
                restaurantFacade.findRestaurantsNearby(latitude, longitude, radius, unit);

        return ResponseEntity.ok().body(restaurantDtoList);
    }

    @GetMapping
    @RequestMapping("/{restaurantId}")
    @PreAuthorize(EnumAuthority.ROLE_RESTAURANT)
    public ResponseEntity getRestaurantById(@RequestAttribute(RequestAttributes.TOKEN_REQ_ATTR) JwtToken jwtToken,
                                            @PathVariable Long restaurantId) {
        logger.trace("getRestaurantById(restaurantId={})", restaurantId);

        RestaurantDTO restaurantDTO = restaurantFacade.getRestaurantById(restaurantId);
        return ResponseEntity.ok().body(restaurantDTO);
    }

}