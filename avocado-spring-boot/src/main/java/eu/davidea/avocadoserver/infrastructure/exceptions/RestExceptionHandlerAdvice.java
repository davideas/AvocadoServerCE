package eu.davidea.avocadoserver.infrastructure.exceptions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;


/**
 * @author steduda
 * @since 11/10/2016
 */
@ControllerAdvice(basePackages = "eu.europa.ec.digit.escmobile.boundary.rest.api")
public class RestExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandlerAdvice.class);

    /* PROJECT EXCEPTIONS */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        Objects.requireNonNull(e);

        HttpStatus status = mapExceptionCode2HttpStatus(e.getExceptionCode());
        ErrorResponse errorResponse = convertServiceException(e);

        logger.debug("HttpStatus={}, {}", status, errorResponse, e);
        return ResponseEntity.status(status).body(errorResponse);
    }

    /* SPRING REQUEST EXCEPTIONS */
    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(ServletRequestBindingException e) {
        return handleAsBadRequest(e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return handleAsBadRequest(e);
    }

    /* GENERIC EXCEPTIONS */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        // DO NOT send the internal stacktrace to the client;
        // A configuration flag could be added for sending the stacktrace in DEV/ACC environment, but not in PROD
        return handleAsInternalServerError(e);
    }

    private ResponseEntity<ErrorResponse> handleAsInternalServerError(Exception e) {
        ErrorResponse errorResponse = createGenericResponse();
        logger.error("HttpStatus={}, {}", HttpStatus.INTERNAL_SERVER_ERROR, errorResponse, e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> handleAsBadRequest(Exception e) {
        ErrorResponse errorResponse = createGenericResponse();
        logger.debug("HttpStatus={}, {}", HttpStatus.BAD_REQUEST, errorResponse, e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private ErrorResponse createGenericResponse() {
        return new ErrorResponse(ExceptionCode.TECHNICAL_ERROR.name(),
                ExceptionCode.TECHNICAL_ERROR.getFriendlyMessage());
    }

    private ErrorResponse convertServiceException(ServiceException e) {
        return new ErrorResponse(e.getCode(), e.getFriendlyMessage());
    }

    private HttpStatus mapExceptionCode2HttpStatus(ExceptionCode code) {
        HttpStatus defaultHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (code) {
            case INVALID_PARAMETER:
            case NOT_FOUND:
                return HttpStatus.BAD_REQUEST;
            case TECHNICAL_ERROR:
                return HttpStatus.INTERNAL_SERVER_ERROR;
            default:
                logger.warn("Do not know how to map ExceptionCode {} to the right HTTP status. Using {} for now",
                        code, defaultHttpStatus);
                return defaultHttpStatus;
        }
    }

}