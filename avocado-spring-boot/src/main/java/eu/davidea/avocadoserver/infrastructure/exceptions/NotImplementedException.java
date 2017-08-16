package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 18/08/2016
 */
public class NotImplementedException extends ServiceException {

    private static final String NOT_IMPL = " not implemented";

    public NotImplementedException(String function) {
        super(ExceptionCode.NOT_IMPLEMENTED, function + NOT_IMPL);
    }

}