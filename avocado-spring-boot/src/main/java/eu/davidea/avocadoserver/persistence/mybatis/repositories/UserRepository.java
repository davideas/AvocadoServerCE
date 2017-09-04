package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.audit.LogQueryStats;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.business.user.UserToken;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("userRepository")
public class UserRepository {

    private final UserMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    UserRepository(UserMapper mapper) {
        this.mapper = mapper;
    }

    @LogQueryStats
    public User findById(Long id) {
        return mapper.findById(id);
    }

    @LogQueryStats
    public User findByUsername(String username) {
        return mapper.findByUsername(username.toLowerCase());
    }

    @LogQueryStats
    public User findByEmail(String email) {
        return mapper.findByEmail(email.toLowerCase());
    }

    @LogQueryStats
    public UserToken findByJti(String jti) {
        return mapper.findByJti(jti);
    }

    @LogQueryStats
    public List<UserToken> getUserTokens(Long userId) {
        return mapper.getUserTokens(userId);
    }

    @LogQueryStats
    public int saveUserToken(UserToken userToken) {
        return mapper.insertUserToken(userToken);
    }

}