package eu.davidea.avocadoserver.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.davidea.avocadoserver.boundary.rest.api.auth.LoginFacade;

import static eu.davidea.avocadoserver.infrastructure.security.RequestAttributes.USER_TOKEN_REQ_ATTR;

/**
 * Filter that orchestrates authentication by using supplied JWT token
 *
 * @author pascal alma
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private LoginFacade loginFacade;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        RequestAttributes requestAttributes = new RequestAttributes(request);
        String token = requestAttributes.getToken();

        if (token != null) {
            JwtUserToken userToken = loginFacade.validateToken(token);

            if (userToken != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userToken, null, userToken.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                request.setAttribute(USER_TOKEN_REQ_ATTR, userToken);
                logger.debug("Authenticated user {}", userToken.getUsername());
            }
        }

        filterChain.doFilter(request, response);
    }

}