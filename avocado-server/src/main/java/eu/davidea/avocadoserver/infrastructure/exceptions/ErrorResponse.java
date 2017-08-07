package eu.davidea.avocadoserver.infrastructure.exceptions;

import java.util.Arrays;
import java.util.UUID;

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
        //needs empty for Test class
    }

    public ErrorResponse(String code, String message) {
        this.id = UUID.randomUUID().toString();
        this.code = code;
        this.message = message;
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
                + "\", target:\"" + target
                + "\", details:\"" + Arrays.toString(details)
                + "\"}";
    }

}