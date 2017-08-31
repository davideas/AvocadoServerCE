package eu.davidea.avocadoserver.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import io.jsonwebtoken.Claims;

public class JwtUserToken implements UserDetails {

    private String jti;
    private String username;
    private String audience;
    private EnumAuthority authority;
    private Date issuedAt;
    private Date expiresAt;

    public JwtUserToken(Claims claims) {
        this.jti = claims.getId();
        this.username = claims.getSubject();
        this.audience = claims.getAudience();
        this.authority = EnumAuthority.valueOf((String) claims.get(RequestAttributes.AUTHORITY));
        this.issuedAt = claims.getIssuedAt();
        this.expiresAt = claims.getExpiration();
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList((GrantedAuthority) () -> authority.name());
    }

    @Override
    public String getPassword() {
        return null;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String getName() {
        // TODO: Need to check database
        return getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO: Need to check database
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO: Need to check database
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return expiresAt == null || expiresAt.toInstant().isAfter(Instant.now());
    }

    @Override
    public boolean isEnabled() {
        // TODO: Need to check database
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
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
        return "JwtUserToken{" +
                "jti=" + jti +
                ", username=" + username +
                ", audience=" + audience +
                ", authority=" + authority +
                ", issuedAt=" + issuedAt +
                ", expiresAt=" + expiresAt +
                "}";
    }

    public String toShortString() {
        return "JwtUserToken{" +
                "username=" + username +
                ", audience=" + audience +
                ", authority=" + authority +
                "}";
    }
}
