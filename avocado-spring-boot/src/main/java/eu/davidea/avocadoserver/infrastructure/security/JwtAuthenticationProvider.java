package eu.davidea.avocadoserver.infrastructure.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Used for checking the token from the request and supply the UserDetails if the token is valid
 *
 * @author pascal alma
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtTokenService jwtTokenValidator;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtToken jwtAuthenticationToken = (JwtToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        JwtUserToken parsedUser = jwtTokenValidator.validateToken(token);

        if (parsedUser == null) {
            throw new eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException("JWT token is not valid");
        }

        //List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

        return parsedUser;
    }

}
