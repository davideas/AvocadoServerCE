package eu.davidea.avocadoserver.business.restaurant;

import eu.davidea.avocadoserver.infrastructure.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumDeleted;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class Restaurant implements AuditableEntity, Serializable {

    private static final long serialVersionUID = 5838467921776717987L;

    private Long id;

    private Date creDate;
    private Date modDate;
    private Date lastLoginDate;

    private String name;
    private Integer tables;
    private Integer places;
    private String languageCode;
    private String countryCode;
    private String currencyCode;
    private Float latitude;
    private Float longitude;

    private String address;
    private String zip;
    private String city;
    private String province;

    private String email;
    private String phone;
    private String website;

    private String openHours;
    private String description;
    private EnumDeleted deleted = EnumDeleted.F;


    public Restaurant() {
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

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTables() {
        return tables;
    }

    public void setTables(Integer tables) {
        this.tables = tables;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String localeCode) {
        this.languageCode = localeCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOpenHours() {
        return openHours;
    }

    public void setOpenHours(String openHours) {
        this.openHours = openHours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumDeleted getDeleted() {
        return deleted;
    }

    public void setDeleted(EnumDeleted deleted) {
        this.deleted = deleted;
    }

}