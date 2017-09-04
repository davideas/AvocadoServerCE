package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;

import java.lang.annotation.*;

/**
 * Annotation for specifying a method access-control expression which will be evaluated to
 * decide whether a method invocation is allowed or not.
 *
 * @author Davide Steduto
 * @since 03/09/2017
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {
    /**
     * @return the type of role for this method invocation.
     */
    EnumAuthority value();
}