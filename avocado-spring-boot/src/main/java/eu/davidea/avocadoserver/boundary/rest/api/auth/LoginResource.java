package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantResource;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
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

    /**
     * Client provides minimal information to register a new account.
     *
     * TODO: @param newProfile object with new account information
     * @return TODO: the JWT Token
     */
    @PostMapping
    @RequestMapping("/signup")
    public ResponseEntity signup() {
        logger.trace("signup()");
        loginFacade.signup();
        return ResponseEntity.ok().build();
    }

    /**
     * Client provides principal and credential in order to obtain the Token necessary
     * to consume the requested services.
     *
     * @param authenticationRequest object containing username and password
     * @return the JWT Token
     */
    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.trace("login()");
        JwtToken jwtToken = loginFacade.login(authenticationRequest);
        return ResponseEntity.ok().body(jwtToken.getToken());
    }

    /**
     * This is nice to have use case.
     * <p>Called once from the client devices at the startup to register user last login info.</p>
     *
     * @param jwtToken Build and verified token
     * @return http status 200
     */
    @PutMapping
    @RequestMapping("/registerLogin")
    public ResponseEntity registerLogin(@RequestAttribute JwtToken jwtToken) {
        logger.trace("register()");
        loginFacade.registerLogin(jwtToken);
        return ResponseEntity.ok().build();
    }

    /**
     * Will remove the user's token from the repository.<br>
     * User must login next time, if he wants to use services.
     * <p>Client device is responsible to removed the token on its side.</p>
     *
     * @param jwtToken Build and verified token
     * @return http status 200
     */
    @PostMapping
    @RequestMapping("/logout")
    public ResponseEntity logout(@RequestAttribute JwtToken jwtToken) {
        logger.trace("logout()");
        loginFacade.logout(jwtToken);
        return ResponseEntity.ok().build();
    }

}
