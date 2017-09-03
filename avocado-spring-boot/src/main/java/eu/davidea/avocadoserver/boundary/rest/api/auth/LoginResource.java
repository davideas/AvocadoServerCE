package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantResource;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Davide
 * @since 27/08/2017
 */
@RestController
@RequestMapping(
        value = "api/v1/auth",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginResource {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantResource.class);

    private LoginFacade loginFacade;

    @Autowired
    public LoginResource(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @PostMapping
    @RequestMapping("/signup")
    public ResponseEntity signup() {
        logger.trace("signup()");
        loginFacade.signup();
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.trace("login()");
        JwtToken jwtToken = loginFacade.login(authenticationRequest);
        return ResponseEntity.ok().body(jwtToken.getToken());
    }

    @PostMapping
    @RequestMapping("/logout")
    public ResponseEntity logout(@RequestBody AuthenticationRequest authentication) {
        logger.trace("logout()");
        loginFacade.logout(authentication);
        return ResponseEntity.ok().build();
    }

}
