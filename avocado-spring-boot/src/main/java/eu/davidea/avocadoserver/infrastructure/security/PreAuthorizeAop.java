package eu.davidea.avocadoserver.infrastructure.security;


import eu.davidea.avocadoserver.infrastructure.exceptions.AuthorizationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Davide Steduto
 * @since 03/09/2017
 */
@Aspect
@Component
public class PreAuthorizeAop {

    @Autowired
    public PreAuthorizeAop() {
    }

    /**
     * Verify user role against {@code @}{@link PreAuthorize} role.
     */
    @Before("execution(@eu.davidea.avocadoserver.infrastructure.security.PreAuthorize * *(..)) && " +
            "args(jwtToken,..) && " +
            "@annotation(preAuthorize)")
    public void authorize(JoinPoint joinPoint, JwtToken jwtToken, PreAuthorize preAuthorize)
            throws AuthorizationException {

        boolean result = jwtToken.getAuthorities().stream().anyMatch(
                authority -> authority == preAuthorize.value());
        if (!result) {
            throw AuthorizationException.notInRole(jwtToken.getSubject(), preAuthorize.value().name());
        }
    }

}