package eu.davidea.avocadoserver.boundary.rest.api.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import eu.davidea.avocadoserver.business.enums.EnumUnitMeasure;
import eu.davidea.avocadoserver.infrastructure.security.JwtUserToken;
import eu.davidea.avocadoserver.infrastructure.security.RequestAttributes;

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
    public ResponseEntity findRestaurantsNearby(@RequestAttribute(RequestAttributes.USER_TOKEN_REQ_ATTR) JwtUserToken userToken,
                                                @RequestParam(name = "name") String name) {

        logger.trace("findRestaurantByName(name={})", name);
        List<RestaurantDTO> restaurantDtoList = restaurantFacade.findRestaurantByName(name);
        return ResponseEntity.ok().body(restaurantDtoList);
    }

    @GetMapping
    @RequestMapping("/findNearby")
    public ResponseEntity findRestaurantsNearby(@RequestAttribute(RequestAttributes.USER_TOKEN_REQ_ATTR) JwtUserToken userToken,
                                                @RequestParam(name = "lat") Float latitude,
                                                @RequestParam(name = "long") Float longitude,
                                                @RequestParam(name = "radius") Short radius,
                                                @RequestParam(name = "unit") EnumUnitMeasure unit)
            throws MissingServletRequestParameterException {

        logger.trace("findRestaurantsNearby(lat={}, long={}, radius={}, unit={})", latitude, longitude, radius, unit);
        List<RestaurantDTO> restaurantDtoList =
                restaurantFacade.findRestaurantsNearby(latitude, longitude, radius, unit);

        return ResponseEntity.ok().body(restaurantDtoList);
    }

    @GetMapping
    @RequestMapping("/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getRestaurantById(@RequestAttribute(RequestAttributes.USER_TOKEN_REQ_ATTR) JwtUserToken userToken,
                                            @PathVariable Long restaurantId) {
        logger.trace("getRestaurantById(restaurantId={})", restaurantId);

        RestaurantDTO restaurantDTO = restaurantFacade.getRestaurantById(restaurantId);
        return ResponseEntity.ok().body(restaurantDTO);
    }

}