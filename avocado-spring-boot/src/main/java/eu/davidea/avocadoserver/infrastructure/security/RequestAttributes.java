package eu.davidea.avocadoserver.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static org.springframework.util.StringUtils.hasLength;


/**
 * @author Davide
 * @since 27/08/2017
 */
public class RequestAttributes {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String USER_TOKEN_REQ_ATTR = "userToken";

    private final HttpServletRequest request;

    public RequestAttributes(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Get the JWT specified via the HTTP <tt>Authorization</tt> header. This method does not validate the JWT,
     * but it ensures that it was specified via the right HTTP header after the &quot;Bearer&nbsp;&quot; string.
     *
     * @return The found JWT or <code>null</code> if no token or invalid token found.
     */
    public String getToken() {
        String authHeader = request.getHeader("Authorization");
        if (hasLength(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // The part after "Bearer "
        }
        return null;
    }

    public String getUserAgent() {
        return request.getHeader("User-Agent");
    }

    /**
     * @return The app version if found as HTTP header <tt>X-App-Version</tt>, <code>null</code> otherwise.
     */
    public String getAppVersion() {
        return request.getHeader("X-App-Version");
    }

    public Locale getAppLocale() {
        String acceptLanguageHeader = this.request.getHeader("Accept-Language");
        if (null == acceptLanguageHeader) {
            logger.warn("No HTTP header Accept-Language found in this request!");
        }
        return getLocaleResolver().resolveLocale(this.request);
    }

    private LocaleResolver getLocaleResolver() {
        WebApplicationContext springContext = RequestContextUtils.findWebApplicationContext(request);
        return springContext.getBean(LocaleResolver.class);
    }

}