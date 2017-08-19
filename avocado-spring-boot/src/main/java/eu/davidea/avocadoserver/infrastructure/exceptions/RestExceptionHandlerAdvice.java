package eu.davidea.avocadoserver.infrastructure.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * @author steduda
 * @since 11/10/2016
 */
@ControllerAdvice(basePackages = "eu.davidea.avocadoserver.boundary.rest.api")
public class RestExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandlerAdvice.class);

    /* PROJECT EXCEPTIONS */

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        HttpStatus status = e.getExceptionCode().getHttpStatus();
        ErrorResponse errorResponse = createCustomResponse(
                e.getCode(), e.getFriendlyMessage(),
                e.getTarget(), e.getDetailsAsArray());

        logger.debug("HttpStatus={}, {}", status, errorResponse, e);
        return ResponseEntity.status(status).body(errorResponse);
    }

    /* SPRING REQUEST EXCEPTIONS */

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(UnsatisfiedServletRequestParameterException e) {
        return handleAsBadRequest(e, e.getParamConditionGroups().toString(), e.getParamConditions());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return handleAsBadRequest(e, e.getParameterName());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleMissingPathVariableException(MissingPathVariableException e) {
        return handleAsBadRequest(e, e.getVariableName());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return handleAsBadRequest(e, e.getName());
    }

    /* GENERIC EXCEPTIONS */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        // DO NOT send the internal stacktrace to the client;
        // A configuration flag could be added for sending the stacktrace in DEV/ACC environment, but not in PROD
        return handleAsInternalServerError(e);
    }

    /* RESPONSES HELPERS */

    private ResponseEntity<ErrorResponse> handleAsInternalServerError(Exception e) {
        ErrorResponse errorResponse = createGenericResponse();
        logger.error("HttpStatus={}, {}", HttpStatus.INTERNAL_SERVER_ERROR, errorResponse, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleAsBadRequest(Exception e, String target, String... details) {
        ErrorResponse errorResponse = createCustomResponse(
                ExceptionCode.INVALID_PARAMETER.name(),
                e.getLocalizedMessage(), target, details);
        logger.debug("HttpStatus={}, {}", HttpStatus.BAD_REQUEST, errorResponse, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private static ErrorResponse createGenericResponse() {
        return new ErrorResponse(ExceptionCode.TECHNICAL_ERROR.name(), ExceptionCode.TECHNICAL_ERROR.getFriendlyMessage());
    }

    private static ErrorResponse createCustomResponse(String code, String message, String target, String... details) {
        return new ErrorResponse(code, message, target, details);
    }

}