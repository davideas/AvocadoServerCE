package eu.davidea.avocadoserver.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.davidea.avocadoserver.boundary.rest.api.auth.LoginFacade;
import eu.davidea.avocadoserver.business.user.JwtToken;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ErrorResponse;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String LOGGED_IN_USER_REQ_ATTR = "loggedInUser";

    private LoginFacade loginFacade;
    private ObjectMapper jsonMapper = new ObjectMapper();

    public JWTInterceptor(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestAttributes requestAttributes = new RequestAttributes(request);
        String token = requestAttributes.getToken();
        try {
            if (token != null) {
                JwtToken tokenObject = loginFacade.validateToken(token);
                request.setAttribute(LOGGED_IN_USER_REQ_ATTR, tokenObject);
            }
            return true;
        } catch (AuthenticationException e) {
            // 401 - for invalid token
            logger.warn("Invalid authentication token {} for URI {}", token, request.getRequestURI(), e);
            handleUnauthorized(response, e.getLocalizedMessage());
        }
        return false;
    }

    private void handleUnauthorized(HttpServletResponse response, String message) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.UNAUTHORIZED.name(), message);
        String json = this.jsonMapper.writeValueAsString(errorResponse);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("WWW-Authenticate", "Bearer realm=\"some token\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();
    }

}