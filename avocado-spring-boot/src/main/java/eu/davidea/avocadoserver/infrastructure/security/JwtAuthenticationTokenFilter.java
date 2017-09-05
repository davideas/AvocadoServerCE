package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.boundary.rest.api.auth.LoginFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import static eu.davidea.avocadoserver.infrastructure.security.RequestAttributes.TOKEN_REQ_ATTR;

/**
 * Filter that orchestrates authentication by using supplied JWT token
 *
 * @author Davide
 * @since 29/08/2017
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
            JwtToken userToken = loginFacade.validateToken(token);

            if (userToken != null) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userToken, null, getGrantedAuthorities(userToken));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                request.setAttribute(TOKEN_REQ_ATTR, userToken);
                logger.debug("Authenticated user {}", userToken.getSubject());
            }
        }

        filterChain.doFilter(request, response);
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(JwtToken userToken) {
        return userToken.getAuthorities().stream().map(
                authority -> (GrantedAuthority) authority::name
        ).collect(Collectors.toList());
    }

}