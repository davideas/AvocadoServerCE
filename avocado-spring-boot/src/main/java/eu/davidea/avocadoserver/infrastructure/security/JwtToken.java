package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Date;

public class JwtToken extends UsernamePasswordAuthenticationToken {

    private String jti;
    private String token;
    private Date issuedAt;
    private Date expiresAt;

    public JwtToken(String jti, String token, Date issuedAt, Date expiresAt) {
        super(null, null);
        this.jti = jti;
        this.token = token;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
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

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Token{jti=" + jti +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                ", token=" + token +
                "}";
    }

    public String toShortString() {
        return "Token{jti=" + jti + "}";
    }

}