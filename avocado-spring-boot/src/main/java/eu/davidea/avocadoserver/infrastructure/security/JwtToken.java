package eu.davidea.avocadoserver.infrastructure.security;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import io.jsonwebtoken.Claims;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Davide Steduto
 * @since 29/08/2017
 */
public class JwtToken {

    private String jti;
    private String token;
    private String subject;
    private EnumAuthority audience;
    private Date issuedAt;
    private Date expiresAt;

    /**
     * Constructor for new built Token after a positive login
     */
    public JwtToken(String subject, EnumAuthority audience, String jti, String token, Date issuedAt, Date expiresAt) {
        this.subject = subject;
        this.audience = audience;
        this.jti = jti;
        this.token = token;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    /**
     * Constructor for incoming Token
     */
    public JwtToken(Claims claims) {
        this.jti = claims.getId();
        this.subject = claims.getSubject();
        this.audience = EnumAuthority.valueOf(claims.getAudience());
        this.issuedAt = claims.getIssuedAt();
        this.expiresAt = claims.getExpiration();
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EnumAuthority getAudience() {
        return audience;
    }

    public void setAudience(EnumAuthority audience) {
        this.audience = audience;
    }

    public List<EnumAuthority> getAuthorities() {
        return Collections.singletonList(audience);
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public boolean isTokenExpired() {
        return expiresAt != null && expiresAt.toInstant().isBefore(Instant.now());
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "jti=" + jti +
                ", username=" + subject +
                ", authority=" + audience +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                "}";
    }

}