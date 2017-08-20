package eu.davidea.avocadoserver.business.menu;

import eu.davidea.avocadoserver.business.audit.AuditableEntity;
import eu.davidea.avocadoserver.business.enums.EnumMenuStatus;

import java.util.Date;

public class Menu implements AuditableEntity {

    private Long id;
    private Long restaurant_id;
    private short order_id;
    private Date creDate;
    private Date modDate;
    private String title;
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

    public Long getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Long restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public short getOrder_id() {
        return order_id;
    }

    public void setOrder_id(short order_id) {
        this.order_id = order_id;
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