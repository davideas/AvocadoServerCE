package eu.davidea.avocadoserver.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.davidea.avocadoserver.boundary.rest.api.auth.LoginFacade;
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

import static eu.davidea.avocadoserver.infrastructure.security.RequestAttributes.USER_TOKEN_REQ_ATTR;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    private LoginFacade loginFacade;
    private ObjectMapper jsonMapper;

    public JwtInterceptor(LoginFacade loginFacade) {
        this.loginFacade = loginFacade;
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestAttributes requestAttributes = new RequestAttributes(request);
        String token = requestAttributes.getToken();
        try {
            if (token == null) {
                throw new AuthenticationException("No JWT token found in request header");
            }
            JwtUserToken userToken = loginFacade.validateToken(token);
            if (userToken == null) {
                throw new AuthenticationException("Token is invalid");
            }
            request.setAttribute(USER_TOKEN_REQ_ATTR, userToken);
            return true;
        } catch (AuthenticationException e) {
            // 401 - for invalid token
            logger.warn("Invalid authentication token for URI {}", request.getRequestURI());
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