package eu.davidea.avocadoserver.business.user;

import eu.davidea.avocadoserver.business.enums.EnumAuthority;
import eu.davidea.avocadoserver.business.enums.EnumUserStatus;

import java.io.Serializable;
import java.util.Date;

public class UserToken implements Serializable {

    private static final long serialVersionUID = -6248231325212580059L;

    private Long id;
    private Long userId;
    private EnumUserStatus status;
    private String jti;
    private String osName;
    private String osVersion;
    private String userAgent;
    private boolean enabled;
    private EnumAuthority authority;
    private Date creDate;
    private Date expDate;
    private Date lastLoginDate;

    public UserToken() {
    }

    public UserToken(Long userId, EnumAuthority authority, String jti) {
        this.userId = userId;
        this.authority = authority;
        this.jti = jti;
        this.creDate = this.lastLoginDate = new Date();
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public EnumUserStatus getStatus() {
        return status;
    }

    public void setStatus(EnumUserStatus status) {
        this.status = status;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public EnumAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(EnumAuthority authority) {
        this.authority = authority;
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

}