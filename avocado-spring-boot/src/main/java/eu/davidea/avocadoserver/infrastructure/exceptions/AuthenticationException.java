package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class AuthenticationException extends ServiceException {

    public AuthenticationException(String technicalMessage) {
        super(ExceptionCode.UNAUTHORIZED, technicalMessage);
    }

    public AuthenticationException(ExceptionCode code) {
        super(code, code.getFriendlyMessage());
    }

    public AuthenticationException(ExceptionCode code, String technicalMessage) {
        super(code, technicalMessage);
        setFriendlyMessage(code.getFriendlyMessage());
    }

    public static AuthenticationException accountLocked() {
        AuthenticationException exception = new AuthenticationException("User account is locked");
        exception.setFriendlyMessage(exception.getLocalizedMessage());
        return exception;
    }

    public static AuthenticationException accountDisabled() {
        AuthenticationException exception = new AuthenticationException("User account is disabled");
        exception.setFriendlyMessage(exception.getLocalizedMessage());
        return exception;
    }

    public static AuthenticationException accountExpired() {
        AuthenticationException exception = new AuthenticationException("User account is expired");
        exception.setFriendlyMessage(exception.getLocalizedMessage());
        return exception;
    }

    public static AuthenticationException credentialsExpired() {
        AuthenticationException exception = new AuthenticationException("User credentials have expired");
        exception.setFriendlyMessage(exception.getLocalizedMessage());
        return exception;
    }

}