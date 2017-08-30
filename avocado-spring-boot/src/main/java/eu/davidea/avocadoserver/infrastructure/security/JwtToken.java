package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

public class JwtToken extends UsernamePasswordAuthenticationToken {

    private String jti;
    private String token;
    private Date expiresAt;

    public JwtToken(String token) {
        super(null, null);
        this.token = token;
    }

    public JwtToken(String jti, String token, Date expiresAt) {
        super(null, null);
        this.jti = jti;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public JwtToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
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

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    @Override
    public String toString() {
        return "Token{jti=" + jti +
                ", expiresAt=" + expiresAt +
                ", token=" + token +
                "}";
    }

    public String toShortString() {
        return "Token{jti=" + jti + "}";
    }

}