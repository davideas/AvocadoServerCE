package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import eu.davidea.avocadoserver.infrastructure.security.JwtUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Service
public class LoginFacade {

    private LoginUseCase loginUseCase;
    private JwtTokenService tokenService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public LoginFacade(LoginUseCase loginUseCase, JwtTokenService tokenService,
                       AuthenticationManager authenticationManager) {
        this.loginUseCase = loginUseCase;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public JwtToken login(AuthenticationRequest authenticationRequest) {
        Objects.requireNonNull(authenticationRequest);

        String login = authenticationRequest.getUsername();
        CharSequence rawPassword = authenticationRequest.getPassword();

        // Perform the security
        // WIth Spring Security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, rawPassword)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // With JWT Interceptor
        //User user = loginUseCase.loginUser(login, rawPassword);
        // WIth Spring Security
        User user = (User) authentication.getPrincipal();
        return tokenService.generateToken(user);
    }

    //@Transactional
    @NotNull
    public JwtUserToken validateToken(String token) {
        Objects.requireNonNull(token);

        JwtUserToken userToken = tokenService.validateToken(token);
//        if (userToken != null) {
//            return loginUseCase.validateUserStatus(userToken);
//        }
        return userToken;
    }

    //@Transactional
    public void logout(AuthenticationRequest authentication) {
        throw new NotImplementedException("logout");
    }

}