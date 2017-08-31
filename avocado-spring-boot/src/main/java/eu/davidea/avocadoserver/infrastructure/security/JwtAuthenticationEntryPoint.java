package eu.davidea.avocadoserver.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.davidea.avocadoserver.infrastructure.exceptions.ErrorResponse;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;
    private ObjectMapper jsonMapper;

    public JwtAuthenticationEntryPoint() {
        this.jsonMapper = new ObjectMapper();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // This is invoked when user tries to access a secured REST resource without supplying
        // any credentials. We send a 401 Unauthorized response because there is no 'login page'
        // to redirect to.
        ErrorResponse errorResponse = new ErrorResponse(ExceptionCode.UNAUTHORIZED.name(), "Unauthorized");
        String json = this.jsonMapper.writeValueAsString(errorResponse);
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setHeader("WWW-Authenticate", "Bearer realm=\"some token\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        out.write(json);
        out.close();
    }

}