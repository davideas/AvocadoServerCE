package eu.davidea.avocadoserver.infrastructure.exceptions;

import eu.davidea.avocadoserver.infrastructure.filters.LoggingMDC;

import java.util.Arrays;

/**
 * @author Davide Steduto
 * @since 04/08/2016
 */
public class ErrorResponse {

    private String id;
    private String code;
    private String message;
    private String target;
    private String[] details;

    public ErrorResponse() {
        // Needs empty for Test class
    }

    public ErrorResponse(String code, String message) {
        this.id = LoggingMDC.getRequestId();
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, String target, String... details) {
        this(code, message);
        // Optimize the output json
        if (target != null) this.target = target;
        if (details != null) this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTarget() {
        return target;
    }

    public ErrorResponse setTarget(String target) {
        this.target = target;
        return this;
    }

    public String[] getDetails() {
        return details;
    }

    public ErrorResponse setDetails(String[] details) {
        this.details = details;
        return this;
    }

    @Override
    public String toString() {
        return "ErrorResponse={id:\"" + id
                + "\", code:\"" + code
                + "\", message:\"" + message
                + (target != null ? "\", target:\"" + target : "")
                + (details != null ? "\", details:\"" + Arrays.toString(details) : "")
                + "\"}";
    }

}