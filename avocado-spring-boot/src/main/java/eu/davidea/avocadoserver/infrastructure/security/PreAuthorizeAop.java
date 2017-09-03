package eu.davidea.avocadoserver.infrastructure.security;


import eu.davidea.avocadoserver.infrastructure.exceptions.AuthorizationException;
import eu.davidea.avocadoserver.infrastructure.statistics.QueryStatsLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PreAuthorizeAop {

    private QueryStatsLogger queryStatsLogger;

    @Autowired
    public PreAuthorizeAop(QueryStatsLogger queryStatsLogger) {
        this.queryStatsLogger = queryStatsLogger;
    }

    /**
     * Verify user role against {@code @}{@link PreAuthorize} role.
     */
    @Before("execution(@eu.davidea.avocadoserver.infrastructure.security.PreAuthorize * *(..)) && " +
            "args(userToken,..) && " +
            "@annotation(preAuthorize)")
    public void authorize(JoinPoint joinPoint, JwtUserToken userToken, PreAuthorize preAuthorize)
            throws AuthorizationException {

        boolean result = userToken.getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(preAuthorize.value().name())
        );
        if (!result) {
            throw AuthorizationException.notInRole(userToken.getUsername(), preAuthorize.value().name());
        }
    }

}