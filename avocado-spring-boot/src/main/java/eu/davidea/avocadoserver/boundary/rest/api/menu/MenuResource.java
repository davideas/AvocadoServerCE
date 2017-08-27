package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantFacade;
import eu.davidea.avocadoserver.boundary.rest.api.restaurant.RestaurantResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Davide
 * @since 06/08/2017
 */
@RestController
@RequestMapping(value = "api/v1/restaurants", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MenuResource {

    private final static Logger logger = LoggerFactory.getLogger(RestaurantResource.class);
    private RestaurantFacade restaurantFacade;
    private MenuFacade menuFacade;

    @Autowired
    public MenuResource(RestaurantFacade restaurantFacade, MenuFacade menuFacade) {
        this.restaurantFacade = restaurantFacade;
        this.menuFacade = menuFacade;
    }

    @GetMapping
    @RequestMapping("/{restaurantId}/menus")
    public ResponseEntity getRestaurantMenu(@PathVariable Long restaurantId, @RequestParam String languageCode) {
        logger.trace("getRestaurantMenus(restaurantId={})", restaurantId);

        List<MenuDTO> menuDTOs = restaurantFacade.getMenus(restaurantId, languageCode);
        return ResponseEntity.ok().body(menuDTOs);
    }

    @GetMapping
    @RequestMapping("/{restaurantId}/menus/{menuId}")
    public ResponseEntity gettMenu(@PathVariable Long restaurantId, @PathVariable Long menuId,
                                            @RequestParam String languageCode) {
        logger.trace("getMenu(restaurantId={}, menuId={})", restaurantId, menuId);

        return ResponseEntity.ok().body("");
    }

}
