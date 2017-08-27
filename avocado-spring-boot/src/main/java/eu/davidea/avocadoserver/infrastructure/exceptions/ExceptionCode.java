package eu.davidea.avocadoserver.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Enumeration dedicated to Error codes where a friendly message and an Http Status are preset.
 * <p>
 * For more info about Http Status codes:
 * <a href="http://racksburg.com/choosing-an-http-status-code/">http://racksburg.com/choosing-an-http-status-code/</a>
 *
 * @author Davide Steduto
 * @since 08/08/2016
 */
public enum ExceptionCode {

    INVALID_PARAMETER(
            HttpStatus.BAD_REQUEST,
            "Required parameter not valid or not present"),
    INVALID_LENGTH(
            HttpStatus.UNAUTHORIZED,
            "Invalid login length"),
    UNAUTHORIZED(
            HttpStatus.UNAUTHORIZED,
            "Invalid username or password"),
    OBJECT_NOT_FOUND(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "No entity found"),
    NOT_IMPLEMENTED(
            HttpStatus.NOT_IMPLEMENTED,
            "Not implemented"),
    TECHNICAL_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Server encountered a technical error :-( Please see server logs");

    private HttpStatus status;
    private String friendlyMessage;

    ExceptionCode(HttpStatus status, String friendlyMessage) {
        this.status = status;
        this.friendlyMessage = friendlyMessage;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }

}