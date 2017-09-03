package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 03/09/2017
 */
public class AuthorizationException extends ServiceException {

    public AuthorizationException() {
        super(ExceptionCode.FORBIDDEN, "User not in role");
    }

    public static AuthorizationException notInRole(String userId, String role) {
        AuthorizationException exception = new AuthorizationException();
        exception.setFriendlyMessage("User " + userId + " doesn't have role " + role + " to execute this method")
                .setTarget("Restaurant");
        return exception;
    }

}