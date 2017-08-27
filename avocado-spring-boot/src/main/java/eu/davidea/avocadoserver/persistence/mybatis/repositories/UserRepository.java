package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.audit.LogQueryStats;
import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

}