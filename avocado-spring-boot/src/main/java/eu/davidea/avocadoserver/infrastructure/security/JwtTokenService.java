package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.business.user.JwtToken;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by morujca on 29/04/2016.
 */
@Service
public class JwtTokenService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String jwtSecret;

    private Key key;

    public JwtTokenService(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @PostConstruct
    public void init() {
        byte[] jwtSecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
        this.key = new SecretKeySpec(jwtSecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public JwtToken generateToken() {
        // expire after 365 days
        Date expireAt = Date.from(LocalDateTime.now().plus(365 * 24, ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant());

        String jti = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .setId(jti)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .compact();

        JwtToken userToken = new JwtToken(jti, token, expireAt);

        logger.debug("Generated user token is: {}", userToken);

        return userToken;
    }

    public void validateToken(String token, TokenValidationResult validationResult) {
        Objects.requireNonNull(token);
        Objects.requireNonNull(validationResult);

        try {
            JwtToken userToken = buildUserToken(token);
            logger.debug("Built user token: {} ", userToken.toShortString());
            validationResult.validToken(userToken);
        } catch (ClaimJwtException expired) {
            logger.debug("Invalid/ expired token for user {} - Id: {}, issued at {} and expired at {}",
                    expired.getClaims().getId(), expired.getClaims().getSubject(), expired.getClaims().getExpiration(), expired);
            validationResult.invalidToken();
        } catch (JwtException e) {
            logger.warn("Invalid token: {}", token, e);
            validationResult.invalidToken();
        }
    }

    private JwtToken buildUserToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.key).parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        Date expiration = body.getExpiration();
        String jti = body.getId();
        return new JwtToken(jti, token, expiration);
    }
}
