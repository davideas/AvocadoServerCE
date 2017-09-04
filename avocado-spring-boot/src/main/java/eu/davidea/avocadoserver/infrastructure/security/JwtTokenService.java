package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.business.user.User;
import eu.davidea.avocadoserver.infrastructure.exceptions.AuthenticationException;
import eu.davidea.avocadoserver.infrastructure.exceptions.ExceptionCode;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * @author Davide Steduto
 * @since 29/08/2017
 */
@Service
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    private final String jwtSecret;
    private final long jwtDuration;
    private Key key;

    public JwtTokenService(@Value("${jwt.secret}") String jwtSecret,
                           @Value("${jwt.duration}") long jwtDuration) {
        this.jwtSecret = jwtSecret;
        this.jwtDuration = jwtDuration;
    }

    @PostConstruct
    public void init() {
        byte[] jwtSecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        this.key = new SecretKeySpec(jwtSecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    /**
     * Generates a new JWT Token to return to the user's device.
     *
     * @param user the User object
     * @return the JWT Token
     */
    @NotNull
    public JwtToken generateToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Date issueAt = Date.from(now
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date expireAt = (jwtDuration >= 0 ? Date.from(now
                .plus(jwtDuration, ChronoUnit.MINUTES) // Expire after X days
                .atZone(ZoneId.systemDefault())
                .toInstant()) : null);

        String jti = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .setId(jti)
                .setSubject(user.getUsername())
                .setAudience(user.getAuthority().name())
                .setIssuedAt(issueAt)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .compact();

        JwtToken jwtToken = new JwtToken(
                user.getUsername(), user.getAuthority(),
                jti, token, issueAt, expireAt);
        logger.debug("Generated token: {}", jwtToken.toString());

        return jwtToken;
    }

    /**
     * Provides a basic first step validation for the incoming token.
     *
     * @param token the incoming token
     * @return the JWT Token object
     * @throws AuthenticationException if token is expired or invalid
     */
    @NotNull
    public JwtToken validateToken(String token) throws AuthenticationException {
        try {
            JwtToken jwtToken = parseToken(token);
            logger.debug("Parsed token: {} ", jwtToken.toString());
            return jwtToken;
        } catch (ClaimJwtException expired) {
            logger.warn("Invalid/Expired token for user={}, audience={}, jti={}, issuedAt={}, expiredAt={}",
                    expired.getClaims().getSubject(), expired.getClaims().getAudience(), expired.getClaims().getId(),
                    expired.getClaims().getIssuedAt(), expired.getClaims().getExpiration());
            // TODO: Should remove user's token from Repository?
            // With JWT Interceptor:
            throw new AuthenticationException(ExceptionCode.TOKEN_EXPIRED);
        } catch (IllegalArgumentException | JwtException e) {
            logger.warn("Invalid token: {}", e.getLocalizedMessage());
            // With JWT Interceptor:
            throw new AuthenticationException(ExceptionCode.INVALID_TOKEN);
        }
        // With Spring Security:
        //return null;
    }

    private JwtToken parseToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token);
        return new JwtToken(claimsJws.getBody());
    }

}