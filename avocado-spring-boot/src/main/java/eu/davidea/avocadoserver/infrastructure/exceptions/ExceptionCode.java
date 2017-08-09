package eu.davidea.avocadoserver.infrastructure.exceptions;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public enum ExceptionCode {

    INVALID_PARAMETER("Required parameter not valid or not present"),
    UNAUTHORIZED("Unauthorized"),
    NOT_FOUND("Not found"),
    NOT_IMPLEMENTED("Not implemented"),

    TECHNICAL_ERROR("Server encountered a technical error :-(");


    private String friendlyMessage;

    ExceptionCode(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

}