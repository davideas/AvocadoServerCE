package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.utilities.EmailValidator;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

//        if (user == null) {
//            throw new AuthenticationException("Invalid username");
//        } else if (verifyPassword(rawPassword, user.getPassword())) {
//            return user;
//        } else {
//            throw new AuthenticationException("Invalid password");
//        }
        return user;
    }

    private boolean verifyPassword(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}