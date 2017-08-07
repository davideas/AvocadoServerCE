package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class DaoException extends AbstractException {

    public DaoException(String technicalMessage) {
        super(ExceptionCode.TECHNICAL_ERROR, technicalMessage);
    }

}