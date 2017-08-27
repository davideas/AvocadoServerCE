package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import eu.davidea.avocadoserver.infrastructure.utilities.EmailValidator;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginUseCase {

    private static final int USERNAME_MIN_LENGTH = 5;
    private UserRepository userRepository;

    @Autowired
    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String login) {
        Objects.requireNonNull(login);

        if (EmailValidator.isValidEmailAddress(login)) {
            return userRepository.findByEmail(login);
        } else if (login.length() < USERNAME_MIN_LENGTH) {
            throw new AuthenticationException(ExceptionCode.INVALID_LENGTH, "Username has invalid length");
        } else {
            return userRepository.findByUsername(login);
        }
    }

}