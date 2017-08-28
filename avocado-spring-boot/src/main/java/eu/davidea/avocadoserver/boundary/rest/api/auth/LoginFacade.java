package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.NotImplementedException;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import eu.davidea.avocadoserver.infrastructure.security.JwtUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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

        return tokenService.validateToken(token);
    }

    //@Transactional
    public void logout(AuthenticationRequest authentication) {
        throw new NotImplementedException("logout");
    }

}