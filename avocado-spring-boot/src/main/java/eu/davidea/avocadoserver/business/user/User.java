package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUserStatus;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * @author Davide
 * @since 27/08/2017
 */
public class User implements AuditableEntity, Serializable, UserDetails, CredentialsContainer {

    private static final long serialVersionUID = -15273229800392269L;

    protected Long id;
    protected Date creDate;
    protected Date modDate;
    protected String username;
    protected String nickname;
    protected String firstname;
    protected String lastname;
    protected String email;
    protected String password;
    protected boolean termsAccepted;
    protected EnumAuthority authority;
    protected EnumUserStatus status;
    protected Date lastPasswordChangeDate;

    public User() {
        creDate = modDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Date getCreDate() {
        return creDate;
    }

    @Override
    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    @Override
    public Date getModDate() {
        return modDate;
    }

    @Override
    public void setModDate(Date modDate) {
        this.modDate = modDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList((GrantedAuthority) () -> authority.name());
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != EnumUserStatus.DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != EnumUserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return lastPasswordChangeDate == null ||
                lastPasswordChangeDate.toInstant()
                        .plus(6, ChronoUnit.MONTHS)
                        .isBefore(Instant.now());
    }

    @Override
    public boolean isEnabled() {
        return status == EnumUserStatus.ACTIVE || status == EnumUserStatus.REGISTERED;
    }

    @Override
    public String getName() {
        if (StringUtils.hasText(firstname) && StringUtils.hasText(lastname)) {
            return firstname + " " + lastname;
        }
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public EnumAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(EnumAuthority authority) {
        this.authority = authority;
    }

    public EnumUserStatus getStatus() {
        return status;
    }

    public void setStatus(EnumUserStatus status) {
        this.status = status;
    }

    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}