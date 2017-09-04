package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class AuthenticationException extends ServiceException {

    public AuthenticationException(String technicalMessage) {
        super(ExceptionCode.UNAUTHORIZED, technicalMessage);
    }

    public AuthenticationException(ExceptionCode tokenExpired) {
        super(tokenExpired, tokenExpired.getFriendlyMessage());
    }

    public AuthenticationException(ExceptionCode invalidToken, String technicalMessage) {
        super(invalidToken, technicalMessage);
        setFriendlyMessage(technicalMessage);
    }

}