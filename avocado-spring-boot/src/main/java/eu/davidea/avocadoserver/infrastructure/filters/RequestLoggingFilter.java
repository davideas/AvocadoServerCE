package eu.davidea.avocadoserver.infrastructure.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Custom filter class dedicated to identify the incoming Request using {@link org.slf4j.MDC}
 * and log extra parameters such as HTTP headers, processing Time and final response Status.
 *
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class RequestLoggingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    public static final String LOG_HEADER_PREFIXES = "logHeaderPrefixes";
    public static final String INCLUDE_QUERY_STRING = "includeQueryString";
    public static final String INCLUDE_HEADERS = "includeHeaders";
    public static final String INCLUDE_CLIENT_INFO = "includeClientInfo";
    public static final String INCLUDE_PAYLOAD = "includePayload";
    public static final String EXCLUDE = "exclude";
    public static final String ENABLED = "enabled";

    private static final String BEFORE_MESSAGE = ">>> START processing HTTP request=";
    private static final String AFTER_MESSAGE = "<<< END processing HTTP request={} elapsed={}ms status={}";
    private static final int MAX_PAYLOAD_LENGTH = 50;
    private static final String REQUEST_ID = "requestId";
    private static final String START_TIME = "startTime";

    private Pattern logHeaderPrefixes = null;
    private Pattern excludePattern = null;
    
    private boolean includeQueryString = false;
    private boolean includeClientInfo = false;
    private boolean includeHeaders = false;
    private boolean includePayload = false;
    private boolean enabled = true;

    @Override
    protected void initFilterBean() throws ServletException {
        assert (getFilterConfig() != null);

        String param = getFilterConfig().getInitParameter(ENABLED);
        if (param != null) {
            enabled = Boolean.valueOf(param);
        }
        logger.debug("{}={} ", ENABLED, enabled);
        if (!enabled) return;

        param = getFilterConfig().getInitParameter(INCLUDE_QUERY_STRING);
        setIncludeQueryString(param != null && Boolean.valueOf(param));
        logger.debug("{}={}", INCLUDE_QUERY_STRING, isIncludeQueryString());

        param = getFilterConfig().getInitParameter(INCLUDE_CLIENT_INFO);
        setIncludeClientInfo(param != null && Boolean.valueOf(param));
        logger.debug("{}={}", INCLUDE_CLIENT_INFO, isIncludeClientInfo());

        param = getFilterConfig().getInitParameter(INCLUDE_HEADERS);
        setIncludeHeaders(param != null && Boolean.valueOf(param));
        logger.debug("{}={}", INCLUDE_HEADERS, isIncludeHeaders());

        param = getFilterConfig().getInitParameter(INCLUDE_PAYLOAD);
        setIncludePayload(param != null && Boolean.valueOf(param));
        logger.debug("{}={}", INCLUDE_PAYLOAD, isIncludePayload());

        param = getFilterConfig().getInitParameter(LOG_HEADER_PREFIXES);
        if (param != null && includeHeaders) {
            logger.debug("{}={}", LOG_HEADER_PREFIXES, param);
            this.logHeaderPrefixes = Pattern.compile(param);
        }

        param = getFilterConfig().getInitParameter(EXCLUDE);
        if (param != null) {
            logger.debug("{}={}", EXCLUDE, param);
            this.excludePattern = Pattern.compile(param);
        }
    }

    /**
     * Set whether the query string should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeQueryString" in the filter definition in {@code web.xml}.
     */
    public void setIncludeQueryString(boolean includeQueryString) {
        this.includeQueryString = includeQueryString;
    }

    /**
     * Return whether the query string should be included in the log message.
     */
    public boolean isIncludeQueryString() {
        return this.includeQueryString;
    }

    /**
     * Set whether the client address and session id should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeClientInfo" in the filter definition in {@code web.xml}.
     */
    public void setIncludeClientInfo(boolean includeClientInfo) {
        this.includeClientInfo = includeClientInfo;
    }

    /**
     * Return whether the client address and session id should be included in the log message.
     */
    public boolean isIncludeClientInfo() {
        return this.includeClientInfo;
    }

    /**
     * Set whether the request headers should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includeHeaders" in the filter definition in {@code web.xml}.
     */
    public void setIncludeHeaders(boolean includeHeaders) {
        this.includeHeaders = includeHeaders;
    }

    /**
     * Return whether the request headers should be included in the log message.
     */
    public boolean isIncludeHeaders() {
        return this.includeHeaders;
    }

    /**
     * Set whether the request payload (body) should be included in the log message.
     * <p>Should be configured using an {@code <init-param>} for parameter name
     * "includePayload" in the filter definition in {@code web.xml}.
     */
    public void setIncludePayload(boolean includePayload) {
        this.includePayload = includePayload;
    }

    /**
     * Return whether the request payload (body) should be included in the log message.
     */
    public boolean isIncludePayload() {
        return this.includePayload;
    }

    /**
     * The default value is "false" so that the filter may log a "before" message at the
     * start of request processing and an "after" message at the end from when the last
     * asynchronously dispatched thread is exiting.
     */
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    /**
     * Determine whether to call the {@link #beforeRequest}/{@link #afterRequest} methods for the current
     * request, i.e. whether logging is currently active (and the log message is worth building).
     *
     * @param request current HTTP request
     * @return {@code true} if the before/after method should get called;
     * {@code false} to proceed with filter chain with no Log
     */
    private boolean shouldLog(HttpServletRequest request) {
        return enabled;
    }

    /**
     * Forwards the request to the next filter in the chain and delegates down to the subclasses
     * to perform the actual request logging both before and after the request is processed.
     *
     * @see #beforeRequest
     * @see #afterRequest
     */
    @SuppressWarnings("NullableProblems")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        setStartAttributes(request);

        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, MAX_PAYLOAD_LENGTH);
        }
        // Log entry request
        boolean shouldLog = shouldLog(requestToUse);
        if (shouldLog && isFirstRequest) {
            beforeRequest(requestToUse);
        }
        try {
            // Process the request!
            filterChain.doFilter(requestToUse, response);
        } finally {
            // Clear the requestId from MDC
            LoggingMDC.removeRequestId();
            // Log processed request & response
            if (shouldLog && !isAsyncStarted(requestToUse)) {
                afterRequest(requestToUse, response);
            }
        }
    }

    /**
     * Allow to initialize custom attributes into the Request to be read later by this filter.
     *
     * @param request the HTTP request
     */
    private static void setStartAttributes(HttpServletRequest request) {
        // Create request unique Id.
        // This will be also returned in the ResponseEntity in case of error.
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        LoggingMDC.setRequestId(requestId);
        request.setAttribute(REQUEST_ID, requestId);
        request.setAttribute(START_TIME, startTime);
    }

    /**
     * Concrete subclasses should implement this method to write a log message <i>before</i> the request is processed.
     *
     * @param request current HTTP request
     */
    private void beforeRequest(HttpServletRequest request) {
        logger.debug(createBeforeMessage(request));
    }

    /**
     * Concrete subclasses should implement this method to write a log message <i>after</i> the request is processed.
     *
     * @param request  current HTTP request
     * @param response the HTTP response
     */
    private void afterRequest(HttpServletRequest request, HttpServletResponse response) {
        String requestId = (String) request.getAttribute(REQUEST_ID);
        long startTime = (Long) request.getAttribute(START_TIME);
        long elapsed = System.currentTimeMillis() - startTime;
        logger.debug(AFTER_MESSAGE, requestId, elapsed, response.getStatus());
    }

    /**
     * Create a log message for the given request, prefix and suffix.
     * <p>If {@code includeQueryString} is {@code true}, then the inner part
     * of the log message will take the form {@code request_uri?query_string};
     * otherwise the message will simply be of the form {@code request_uri}.
     * <p>The final message is composed of the inner part as described and
     * the supplied prefix and suffix.
     */
    private String createBeforeMessage(HttpServletRequest request) {
        StringBuilder message = new StringBuilder();
        message.append(BEFORE_MESSAGE)
                .append(request.getAttribute(REQUEST_ID))
                .append(" uri=")
                .append(request.getMethod())
                .append(" ")
                .append(request.getRequestURL());

        if (isIncludeQueryString()) {
            String queryString = request.getQueryString();
            if (queryString != null) {
                message.append('?').append(queryString);
            }
        }

        if (isIncludeClientInfo()) {
            String client = request.getRemoteAddr();
            if (StringUtils.hasLength(client)) {
                message.append(" Client=").append(client);
            }
            HttpSession session = request.getSession(false);
            if (session != null) {
                message.append(" Session=").append(session.getId());
            }
            String user = request.getRemoteUser();
            if (user != null) {
                message.append(" User=").append(user);
            }
        }

        if (isIncludeHeaders() && shouldLogHeaders(request)) {
            message.append(" Headers=").append(new ServletServerHttpRequest(request).getHeaders());
        } else {
            message.append(" User-Agent=").append(getUserAgent(request));
        }

        if (isIncludePayload()) {
            ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    int length = Math.min(buf.length, MAX_PAYLOAD_LENGTH);
                    String payload;
                    try {
                        payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                        payload = "<unknown>";
                    }
                    message.append("\nPayload=").append(payload);
                }
            }
        }

        return message.toString();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (excludePattern != null) {
            String path = request.getRequestURI();
            return excludePattern.matcher(path).find();
        }
        return false;
    }

    private boolean shouldLogHeaders(HttpServletRequest request) {
        if (logHeaderPrefixes != null) {
            String path = request.getRequestURI();
            return logHeaderPrefixes.matcher(path).find();
        }
        return false;
    }

    private String getUserAgent(HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");
        if (userAgent == null) {
            userAgent = httpRequest.getHeader("user-agent");
            if (userAgent == null) {
                userAgent = "<none>";
            }
        }
        return userAgent;
    }

}
