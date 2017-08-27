package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Davide
 * @since 27/08/2017
 */
@RestController
@RequestMapping(value = "api/v1/auth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginResource {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantResource.class);

    @Autowired
    public LoginResource() {
    }

    @PostMapping
    @RequestMapping("/restaurant")
    public ResponseEntity loginRestaurant(@RequestAttribute("authentication") Authentication authentication) {
        logger.trace("loginRestaurant()");

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/user")
    public ResponseEntity loginUser(@RequestAttribute("authentication") Authentication authentication) {
        logger.trace("loginUser()");

        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/logout")
    public ResponseEntity logout(@RequestAttribute("authentication") Authentication authentication) {
        logger.trace("logout()");

        return ResponseEntity.ok().build();
    }

}
