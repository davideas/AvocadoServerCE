package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 03/09/2017
 */
public class AuthorizationException extends ServiceException {

    public AuthorizationException(String technicalMessage) {
        super(ExceptionCode.FORBIDDEN, technicalMessage);
    }

    public static AuthorizationException notInRole(String username, String role) {
        AuthorizationException exception = new AuthorizationException("User " + username + " doesn't have role " + role + " to execute this method");
        exception.setFriendlyMessage("User " + username + "not in role")
                .setTarget(role);
        return exception;
    }

    public static AuthorizationException userLocked(String username) {
        AuthorizationException exception = new AuthorizationException("User " + username + " has been locked");
        exception.setFriendlyMessage(exception.getLocalizedMessage());
        return exception;
    }

    public static AuthorizationException tokenDisabled(String username, String jti) {
        AuthorizationException exception = new AuthorizationException("User " + username + " has disabled token, jti=" + jti);
        exception.setFriendlyMessage("Token has been disabled");
        return exception;
    }

}