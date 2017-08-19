package eu.davidea.avocadoserver.infrastructure.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Filter class dedicated to identify the the request and log extra parameters
 * such as HTTP header or user-agent.
 *
 * @author Davide Steduto
 * @since 08/08/2016
 */
@Component
public class LogRequestIDFilter extends AbstractRequestLoggingFilter {

    public static final String LOG_HEADER_PREFIXES_PARAM = "logHeaderPrefixes";
    public static final String EXCLUDE_PARAM = "exclude";

    private final static Logger LOGGER = LoggerFactory.getLogger(LogRequestIDFilter.class);
    private Pattern logHeaderPrefixes = null;
    private Pattern excludePattern = null;

    public LogRequestIDFilter() {
    }

//    @Override
//    protected void initFilterBean() throws ServletException {
//        String headersRegex = getFilterConfig().getInitParameter(LOG_HEADER_PREFIXES_PARAM);
//        if (null != headersRegex) {
//            this.logHeaderPrefixes = Pattern.compile(headersRegex);
//        }
//
//        String excludeRegex = getFilterConfig().getInitParameter(EXCLUDE_PARAM);
//        if (null != excludeRegex) {
//            this.excludePattern = Pattern.compile(excludeRegex);
//        }
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Create request unique Id.
        // This will be also used in the ResponseEntity in case of error.
        final String requestId = UUID.randomUUID().toString();
        final long startTime = System.currentTimeMillis();
        LoggingMDC.setRequestId(requestId);

        // Proceed with body caching
        boolean isFirstRequest = !isAsyncDispatch(request);
        HttpServletRequest requestToUse = request;

        if (isIncludePayload() && isFirstRequest && !(request instanceof ContentCachingRequestWrapper)) {
            requestToUse = new ContentCachingRequestWrapper(request, getMaxPayloadLength());
        }

        boolean shouldLog = shouldLog(requestToUse);
        if (shouldLog && isFirstRequest) {
            logStartRequest(requestId, request);
        }

        try {
            // Continue with the Request!
            filterChain.doFilter(requestToUse, response);
        } finally {
            // Clear the requestId
            LoggingMDC.removeRequestId();
            if (shouldLog && !isAsyncStarted(requestToUse)) {
                logEndRequest(requestId, response.getStatus(), startTime);
            }
        }
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // Not used
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        // Not used
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (null != excludePattern) {
            String path = request.getRequestURI();
            return excludePattern.matcher(path).find();
        }
        return false;
    }

    private void logStartRequest(String requestId, HttpServletRequest httpRequest) {
        if (shouldLogHeaders(httpRequest)) {
            LOGGER.debug(">>> START processing HTTP request {} for method/ URL: {} and \n\theaders: {}",
                    requestId,
                    getMethodAndRequestURL(httpRequest),
                    getHeaders(httpRequest));
        } else {
            LOGGER.debug(">>> START processing HTTP request {} for method/ URL: {}\nUser-Agent: {}",
                    requestId,
                    getMethodAndRequestURL(httpRequest),
                    getUserAgent(httpRequest));
        }
    }

    private void logEndRequest(String requestId, int status, long startTime) {
        long elapsed = System.currentTimeMillis() - startTime;
        LOGGER.debug("<<< END processing HTTP request {} in {} seconds with status {}", requestId,
                String.format(Locale.ENGLISH, "%.3f", elapsed / 1000.0),
                status);
    }

    private String getUserAgent(HttpServletRequest httpRequest) {
        String userAgent = httpRequest.getHeader("User-Agent");
        if (null == userAgent) {
            userAgent = httpRequest.getHeader("user-agent");
            if (null == userAgent) {
                userAgent = "<none>";
            }
        }
        return userAgent;
    }

    private Map<String, String> getHeaders(HttpServletRequest httpReq) {
        Map<String, String> headers = new LinkedHashMap<>();
        Enumeration<String> headerNames = httpReq.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.put(name, toString(httpReq.getHeaders(name)));
        }
        return headers;
    }

    private String toString(Enumeration<String> enumeration) {
        StringBuilder builder = new StringBuilder("[");
        while (enumeration.hasMoreElements()) {
            builder.append(enumeration.nextElement());
            if (enumeration.hasMoreElements()) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    private String getMethodAndRequestURL(ServletRequest request) {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            return httpRequest.getMethod() + "/ " + getRequestURL(httpRequest);
        } catch (Exception e) {
            // Unexpected
            LOGGER.warn("Could not get HTTP method and request URL from request: {}", request, e);
            return "<unknown>";
        }
    }

    private String getRequestURL(HttpServletRequest httpRequest) {
        String requestURL = httpRequest.getRequestURL().toString();
        String queryString = httpRequest.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            requestURL = requestURL + "?" + queryString;
        }
        return requestURL;
    }

    private boolean shouldLogHeaders(HttpServletRequest request) {
        if (null != logHeaderPrefixes) {
            String path = request.getRequestURI();
            return logHeaderPrefixes.matcher(path).find();
        }
        return false;
    }

}