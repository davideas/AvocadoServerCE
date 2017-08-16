package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class ServiceException extends AbstractException {

    public ServiceException(ExceptionCode code, String technicalMessage) {
        super(code, technicalMessage);
    }

}