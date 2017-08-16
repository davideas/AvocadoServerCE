package eu.davidea.avocadoserver.infrastructure.filters;

import org.slf4j.MDC;

/**
 * Utility class for putting and removing keys from the MDC (Mapped Diagnostic Context).
 *
 * @author Davide Steduto
 * @since 08/08/2016
 */
final class LoggingMDC {

    private static final String ARG_REQ_ID = "requestId";

    /**
     * Set the {@code requestId} key to MDC.
     *
     * @param requestId The value to be set. Cannot be {@code null}.
     * @throws IllegalArgumentException if {@code requestId} is {@code null}
     */
    static void setRequestId(String requestId) {
        MDC.put(ARG_REQ_ID, requestId);
    }

    /**
     * Remove any previously set {@code requestId} key from MDC.
     */
    static void removeRequestId() {
        MDC.remove(ARG_REQ_ID);
    }

}