package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import eu.davidea.avocadoserver.infrastructure.security.JwtUserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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
    public JwtToken login(AuthenticationRequest authenticationRequest) {
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
        JwtToken token = tokenService.generateToken(user);
        loginUseCase.saveUserToken(user, token);

        return token;
    }

    @Transactional(readOnly = true)
    @NotNull
    public JwtUserToken validateToken(String token) {
        Objects.requireNonNull(token);

        JwtUserToken jwtUserToken = tokenService.validateToken(token);
        if (jwtUserToken != null) {
            loginUseCase.validateUserStatus(jwtUserToken.getJti());
        }
        return jwtUserToken;
    }

    @Transactional
    public void signup() {
        throw new NotImplementedException("signup");
    }

    @Transactional
    public void logout(AuthenticationRequest authentication) {
        throw new NotImplementedException("logout");
    }

}