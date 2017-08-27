package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUserStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Davide
 * @since 27/08/2017
 */
public class User implements AuditableEntity, Serializable {

    private static final long serialVersionUID = -15273229800392269L;

    private Long id;
    private Date creDate;
    private Date modDate;
    private String username;
    private String nickname;
    private String firstname;
    private String lastname;
    private String email;
    private CharSequence password;
    private boolean termsAccepted;
    private EnumAuthority authority;
    private EnumUserStatus status;
    private Date lastPasswordChangeDate;

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

    public CharSequence getPassword() {
        return password;
    }

    public void setPassword(CharSequence password) {
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

}