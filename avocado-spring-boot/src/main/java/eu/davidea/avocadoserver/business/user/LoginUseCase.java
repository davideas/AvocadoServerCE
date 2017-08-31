package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.utilities.EmailValidator;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUser(username, null);
    }

    @NotNull
    public User loginUser(String login, CharSequence rawPassword) {
        Objects.requireNonNull(login);

//        if (!StringUtils.hasText(rawPassword)) {
//            throw new AuthenticationException("Password has invalid length");
//        }

        User user;
        if (EmailValidator.isValidEmailAddress(login)) {
            user = userRepository.findByEmail(login);
        } else if (login.trim().length() < USERNAME_MIN_LENGTH) {
            throw new AuthenticationException("Username has invalid length");
        } else {
            user = userRepository.findByUsername(login);
        }
        // Performs checks on User
        //performChecks(user, rawPassword);
        // Erase credential from memory
        //rawPassword = null;

        return user;
    }

    public void performChecks(User user, String rawPassword) {
        if (user == null) {
            logger.debug("User not found");
            throw new AuthenticationException("Invalid username or password");
        }
        logger.info("Found user {}", user.getUsername());
        if (!user.isAccountNonLocked()) {
            logger.debug("User account is locked");
            throw new LockedException("User account is locked");
        }
        if (!user.isEnabled()) {
            logger.debug("User account is disabled");
            throw new DisabledException("User is disabled");
        }
        if (!user.isAccountNonExpired()) {
            logger.debug("User account is expired");
            throw new AccountExpiredException("User account is expired");
        }
        if (!user.isCredentialsNonExpired()) {
            logger.debug("User account credentials have expired");
            throw new CredentialsExpiredException("User credentials have expired");
        }
        if (!verifyPassword(rawPassword, user.getPassword())) {
            logger.debug("Password doesn't match");
            throw new AuthenticationException("Invalid username or password");
        }
    }

    private boolean verifyPassword(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}