package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
class ServiceException extends AbstractException {

    ServiceException(ExceptionCode code, String technicalMessage) {
        super(code, technicalMessage);
    }

}