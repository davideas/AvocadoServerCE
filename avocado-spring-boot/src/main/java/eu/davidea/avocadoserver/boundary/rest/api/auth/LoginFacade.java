package eu.davidea.avocadoserver.boundary.rest.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import eu.davidea.avocadoserver.infrastructure.security.JwtUserToken;

@Service
public class LoginFacade {

    private LoginUseCase loginUseCase;
    private JwtTokenService tokenService;

    @Autowired
    public LoginFacade(LoginUseCase loginUseCase, JwtTokenService tokenService) {
        this.loginUseCase = loginUseCase;
        this.tokenService = tokenService;
    }

    @Transactional
    public JwtToken login(AuthenticationRequest authentication) {
        Objects.requireNonNull(authentication);

        String login = authentication.getUsername();
        CharSequence rawPassword = authentication.getPassword();

        User user = loginUseCase.loginUser(login, rawPassword);
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