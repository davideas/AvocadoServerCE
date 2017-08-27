package eu.davidea.avocadoserver.business.restaurant;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumRestaurantStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Davide Steduto
 * @since 08/08/2016
 */
public class Restaurant implements AuditableEntity, Serializable {

    private static final long serialVersionUID = -1772933895066564128L;

    private Long id;
    private Date creDate;
    private Date modDate;
    private Long userId;
    private String name;
    private EnumRestaurantStatus status;
    private String languageCode;
    private String countryCode;
    private String currencyCode;
    private Float latitude;
    private Float longitude;
    private Short distance;

    private String displayAddress;
    private String displayZip;
    private String displayCity;
    private String displayProvince;
    private String displayEmail;
    private String displayPhone;
    private String website;
    private Short tables;
    private Short places;
    private String description;

    public Restaurant() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumRestaurantStatus getStatus() {
        return status;
    }

    public void setStatus(EnumRestaurantStatus status) {
        this.status = status;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
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

    public Short getDistance() {
        return distance;
    }

    public void setDistance(Short distance) {
        this.distance = distance;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }

    public String getDisplayZip() {
        return displayZip;
    }

    public void setDisplayZip(String displayZip) {
        this.displayZip = displayZip;
    }

    public String getDisplayCity() {
        return displayCity;
    }

    public void setDisplayCity(String displayCity) {
        this.displayCity = displayCity;
    }

    public String getDisplayProvince() {
        return displayProvince;
    }

    public void setDisplayProvince(String displayProvince) {
        this.displayProvince = displayProvince;
    }

    public String getDisplayEmail() {
        return displayEmail;
    }

    public void setDisplayEmail(String displayEmail) {
        this.displayEmail = displayEmail;
    }

    public String getDisplayPhone() {
        return displayPhone;
    }

    public void setDisplayPhone(String displayPhone) {
        this.displayPhone = displayPhone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Short getTables() {
        return tables;
    }

    public void setTables(Short tables) {
        this.tables = tables;
    }

    public Short getPlaces() {
        return places;
    }

    public void setPlaces(Short places) {
        this.places = places;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}