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
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

/**
 * Created by morujca on 29/04/2016.
 */
@Service
public class JwtTokenService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    static final String AUTHORITY = "aut";

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

    public JwtToken generateToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        Date issueAt = Date.from(now
                .atZone(ZoneId.systemDefault())
                .toInstant());
        Date expireAt = Date.from(now
                .plus(365, ChronoUnit.DAYS) // Expire after 365 days
                .atZone(ZoneId.systemDefault())
                .toInstant());

        String jti = UUID.randomUUID().toString();
        String token = Jwts.builder()
                .setId(jti)
                .setSubject(user.getUsername())
                //.setAudience()
                .claim(AUTHORITY, user.getAuthority())
                .setIssuedAt(issueAt)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .compact();

        JwtToken userToken = new JwtToken(jti, token, expireAt);

        logger.debug("Generated user token is: {}", userToken);

        return userToken;
    }

    public JwtUserToken validateToken(String token) {
        try {
            JwtUserToken userToken = buildUserToken(token);
            logger.debug("Built user token: {} ", userToken.toShortString());
            return userToken;
        } catch (ClaimJwtException expired) {
            logger.debug("Invalid/Expired token for user={}, jti={}, issuedAt={}, expiredAt={}",
                    expired.getClaims().getId(), expired.getClaims().getSubject(), expired.getClaims().getExpiration());
            throw new AuthenticationException(ExceptionCode.FORBIDDEN, "Token is expired");
        } catch (IllegalArgumentException | JwtException e) {
            logger.warn("Invalid token: {}", e.getLocalizedMessage());
            throw new AuthenticationException(ExceptionCode.FORBIDDEN, "Token is invalid");
        }
    }

    private JwtUserToken buildUserToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token);
        return new JwtUserToken(claimsJws.getBody());
    }

}