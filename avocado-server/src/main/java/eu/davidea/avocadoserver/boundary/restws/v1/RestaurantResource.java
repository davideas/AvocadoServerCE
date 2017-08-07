package eu.davidea.avocadoserver.boundary.restws.v1;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Davide
 * @since 06/08/2017
 */
@RestController
@RequestMapping(value = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class RestaurantResource {

    @GetMapping
    @RequestMapping()
    public ResponseEntity findRestaurantsNearby(@RequestParam(name = "name", required = false) String name,
                                                @RequestParam(name = "lat", required = false) Float latitude,
                                                @RequestParam(name = "long", required = false) Float longitude,
                                                @RequestParam(name = "radius", required = false) Float radius)
            throws MissingServletRequestParameterException {


        return ResponseEntity.ok().body("OK");
    }

    @GetMapping
    @RequestMapping("/{restaurantId}")
    public ResponseEntity getRestaurantById(@PathVariable Long restaurantId) {

        return ResponseEntity.ok().body("OK");
    }

}