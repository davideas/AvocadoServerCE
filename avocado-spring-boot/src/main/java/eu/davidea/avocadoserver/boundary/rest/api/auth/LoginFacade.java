package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthorizationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Davide Steduto
 * @since 28/08/2017
 */
@Service
public class LoginFacade {

    private static final Logger logger = LoggerFactory.getLogger(LoginFacade.class);

    private LoginUseCase loginUseCase;
    private JwtTokenService tokenService;

    // With Spring Security
    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    public LoginFacade(LoginUseCase loginUseCase, JwtTokenService tokenService) {
        this.loginUseCase = loginUseCase;
        this.tokenService = tokenService;
    }

    @Transactional
    public JwtToken login(AuthenticationRequest authenticationRequest, Device device) {
        Objects.requireNonNull(authenticationRequest);

        String login = authenticationRequest.getUsername();
        CharSequence rawPassword = authenticationRequest.getPassword();

        // Perform the security:
        // - With Spring Security
//        final Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(login, rawPassword)
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        User user = (User) authentication.getPrincipal();
        // - With JWT Interceptor
        User user = loginUseCase.loginUser(login, rawPassword);

        JwtToken token = tokenService.generateToken(user, device);
        loginUseCase.saveUserToken(user, token);
        //TODO: Clear very old user tokens Async?

        return token;
    }

    @Transactional
    public void registerLogin(JwtToken jwtToken) {
        Objects.requireNonNull(jwtToken);

        loginUseCase.registerLogin(jwtToken);
    }

    @Transactional(readOnly = true)
    @NotNull
    public JwtToken validateToken(String token) throws AuthenticationException, AuthorizationException {
        Objects.requireNonNull(token);

        // Check incoming Token
        JwtToken jwtToken = tokenService.validateToken(token);
        // Check Token's data against Repository's data
        if (jwtToken != null) {
            loginUseCase.validateUserToken(jwtToken);
        }
        return jwtToken;
    }

    @Transactional
    public void signup() {
        // TODO: Implement user registration
        throw new NotImplementedException("signup");
    }

    @Transactional
    public void logout(JwtToken jwtToken) {
        Objects.requireNonNull(jwtToken);

        loginUseCase.logout(jwtToken);
    }

}