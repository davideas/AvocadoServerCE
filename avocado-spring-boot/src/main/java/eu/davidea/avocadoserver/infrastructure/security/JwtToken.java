package eu.davidea.avocadoserver.infrastructure.security;

import java.util.Date;

public class JwtToken {

    private String jti;
    private String token;
    private Date expiresAt;

    public JwtToken(String jti, String token, Date expiresAt) {
        this.jti = jti;
        this.token = token;
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