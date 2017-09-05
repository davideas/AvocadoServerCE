package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthorizationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import eu.davidea.avocadoserver.infrastructure.security.JwtToken;
import eu.davidea.avocadoserver.infrastructure.utilities.EmailValidator;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;

/**
 * @author Davide Steduto
 * @since 28/08/2017
 */
@Service
public class LoginUseCase implements UserDetailsService {

    private final static Logger logger = LoggerFactory.getLogger(LoginUseCase.class);
    private static final int USERNAME_MIN_LENGTH = 5;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public LoginUseCase(UserRepository userRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(10, java.security.SecureRandom.getInstanceStrong());
    }

    /* With Spring Security */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (EmailValidator.isValidEmailAddress(username)) {
            return userRepository.findByEmail(username);
        } else if (username.trim().length() < USERNAME_MIN_LENGTH) {
            throw new AuthenticationException("Username has invalid length");
        } else {
            return userRepository.findByUsername(username);
        }
    }

    /* With JWT Interceptor */
    @NotNull
    public User loginUser(String login, CharSequence rawPassword) {
        // Validate fields
        if (!StringUtils.hasText(login)) {
            throw new AuthenticationException("Login has invalid length");
        }
        if (!StringUtils.hasText(rawPassword)) {
            throw new AuthenticationException("Password has invalid length");
        }

        User user;
        if (EmailValidator.isValidEmailAddress(login)) {
            user = userRepository.findByEmail(login);
        } else if (login.trim().length() < USERNAME_MIN_LENGTH) {
            throw new AuthenticationException("Username has invalid length");
        } else {
            user = userRepository.findByUsername(login);
        }
        // Performs checks on User
        performUserChecks(user, rawPassword);

        // Erase credential from memory
        user.eraseCredentials();

        return user;
    }

    /* With JWT Interceptor */
    private void performUserChecks(User user, CharSequence rawPassword) throws AuthenticationException {
        if (user == null) {
            logger.debug("User not found");
            throw new AuthenticationException("User not found");
        }
        logger.info("Found user {}", user.getUsername());
        if (!user.isAccountNonLocked()) {
            logger.debug("User account is locked");
            throw AuthenticationException.accountLocked();
        }
        if (!user.isEnabled()) {
            logger.debug("User account is disabled");
            throw AuthenticationException.accountDisabled();
        }
        if (!user.isAccountNonExpired()) {
            logger.debug("User account is expired");
            throw AuthenticationException.accountExpired();
        }
        if (!user.isCredentialsNonExpired()) {
            logger.debug("User account credentials have expired");
            throw AuthenticationException.credentialsExpired();
        }
        if (!verifyPassword(rawPassword, user.getPassword())) {
            logger.debug("Password doesn't match");
            throw new AuthenticationException("Password doesn't match");
        }
    }

    /* With JWT Interceptor */
    private boolean verifyPassword(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void saveUserToken(User user, JwtToken token) {
        UserToken userToken = new UserToken(user.getId(), user.getAuthority(), token.getJti());
        userToken.setExpDate(token.getExpiresAt());
        userRepository.saveUserToken(userToken);
    }

    /**
     * Provides a 2nd step validation for JWT Token against Repository's data.
     * <p>Here we check account and token status.</p>
     *
     * @param jwtToken the JWT Token object
     * @throws AuthenticationException if token not found
     * @throws AuthorizationException if account or token status is locked
     */
    public void validateUserToken(JwtToken jwtToken) throws AuthenticationException, AuthorizationException {
        UserToken userToken = userRepository.findByJti(jwtToken.getJti());

        if (userToken == null) {
            // There's a problem finding the token or token was removed from Repository
            // In any case, token cannot pass 2nd step validation
            throw new AuthenticationException(ExceptionCode.INVALID_TOKEN, "Token not found");
        }
        if (!userToken.isAccountNonLocked()) {
            throw AuthorizationException.userLocked(jwtToken.getSubject());
        }
        if (!userToken.isEnabled()) {
            throw AuthorizationException.tokenDisabled(jwtToken.getSubject(), userToken.getJti());
        }
    }

    public void registerLogin(JwtToken jwtToken) {
        if (userRepository.updateLastLoginDate(jwtToken.getJti())) {
            logger.warn("registerLogin: Token to update was not found!");
        }
    }

    public void logout(JwtToken jwtToken) {
        if (userRepository.deleteUserToken(jwtToken.getJti())) {
            logger.warn("Logout: Token to delete was not found!");
        }
    }

}