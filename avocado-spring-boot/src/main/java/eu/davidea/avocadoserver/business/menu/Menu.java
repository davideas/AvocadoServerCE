package eu.davidea.avocadoserver.business.menu;

import java.util.Date;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumMenuStatus;

public class Menu implements AuditableEntity {

    private Long id;
    private Long restaurantId;
    private Long translationId;
    private short orderId;
    private Date creDate;
    private Date modDate;
    private String title; //This is actually the title of the menu translated
    private EnumMenuStatus status;

    public Menu() {
        creDate = modDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getTranslationId() {
        return translationId;
    }

    public void setTranslationId(Long translationId) {
        this.translationId = translationId;
    }

    public short getOrderId() {
        return orderId;
    }

    public void setOrderId(short orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumMenuStatus getStatus() {
        return status;
    }

    public void setStatus(EnumMenuStatus status) {
        this.status = status;
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

}