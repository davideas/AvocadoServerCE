package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class AuthenticationException extends AbstractException {

    public AuthenticationException(String technicalMessage) {
        super(ExceptionCode.UNAUTHORIZED, technicalMessage);
    }

    public AuthenticationException(ExceptionCode code, String technicalMessage) {
        super(code, technicalMessage);
    }

}