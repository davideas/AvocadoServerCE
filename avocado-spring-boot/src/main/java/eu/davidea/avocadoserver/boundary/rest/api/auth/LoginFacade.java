package eu.davidea.avocadoserver.boundary.rest.api.auth;

import eu.davidea.avocadoserver.business.user.JwtToken;
import eu.davidea.avocadoserver.business.user.LoginUseCase;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import eu.davidea.avocadoserver.infrastructure.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public JwtToken login(String login) {
        Objects.requireNonNull(login);

        User user = loginUseCase.loginUser(login);
        if (user != null) {
            return tokenService.generateToken(user);
        }
        throw new AuthenticationException(ExceptionCode.UNAUTHORIZED, "User not found");
    }

    public boolean validateToken(String token) {
        return tokenService.validateToken(token);
    }

}