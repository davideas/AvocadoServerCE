package eu.davidea.avocadoserver.infrastructure.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
abstract class AbstractException extends RuntimeException {

    private String code;
    private String friendlyMessage;
    private String target;
    private List<String> details;

    AbstractException(ExceptionCode code, String technicalMessage) {
        super(technicalMessage);
        this.code = code.toString();
        this.friendlyMessage = code.getFriendlyMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFriendlyMessage() {
        return friendlyMessage;
    }

    public AbstractException setFriendlyMessage(String friendlyMessage) {
        this.friendlyMessage = friendlyMessage;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public AbstractException setTarget(String target) {
        this.target = target;
        return this;
    }

    public List<String> getDetails() {
        return details;
    }

    public String[] getDetailsAsArray() {
        return details.toArray(new String[0]);
    }

    public AbstractException addDetail(String detail) {
        if (details == null) {
            details = new ArrayList<>(1);
        }
        details.add(detail);
        return this;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

}