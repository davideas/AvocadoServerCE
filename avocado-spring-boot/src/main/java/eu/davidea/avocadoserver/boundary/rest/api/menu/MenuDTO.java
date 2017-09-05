package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.business.enums.EnumMenuStatus;
import eu.davidea.avocadoserver.business.menu.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class MenuDTO {

    private Long id;
    private Long restaurantId;
    private short orderId;
    private Date creDate;
    private Date modDate;
    private String title; //This is actually the title of the menu translated
    private EnumMenuStatus status;

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

    public short getOrderId() {
        return orderId;
    }

    public void setOrderId(short orderId) {
        this.orderId = orderId;
    }

    public Date getCreDate() {
        return creDate;
    }

    public void setCreDate(Date creDate) {
        this.creDate = creDate;
    }

    public Date getModDate() {
        return modDate;
    }

    public void setModDate(Date modDate) {
        this.modDate = modDate;
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

    public static List<MenuDTO> toDto(List<Menu> menus) {
        List<MenuDTO> restaurantDtoList = new ArrayList<>(menus.size());
        restaurantDtoList.addAll(menus.stream().map(MenuDTO::toDto).collect(Collectors.toList()));
        return restaurantDtoList;
    }

    public static MenuDTO toDto(Menu menu) {
        MenuDTO menuDTO = new MenuDTO();

        menuDTO.setId(menu.getId());
        menuDTO.setCreDate(menu.getCreDate());
        menuDTO.setModDate(menu.getModDate());
        menu.setOrderId(menu.getOrderId());
        menu.setRestaurantId(menu.getRestaurantId());
        menuDTO.setStatus(menu.getStatus());
        menuDTO.setTitle(menu.getTitle());

        return menuDTO;
    }

}