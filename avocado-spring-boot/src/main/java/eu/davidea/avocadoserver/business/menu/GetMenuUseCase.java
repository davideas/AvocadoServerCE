package eu.davidea.avocadoserver.business.menu;

import eu.davidea.avocadoserver.persistence.mybatis.repositories.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Davide
 * @since 07/08/2017
 */
@Service
public class GetMenuUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetMenuUseCase.class);

    private MenuRepository menuRepository;

    @Autowired
    public GetMenuUseCase(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getMenus(Long restaurantId, String languageCode) {
        Objects.requireNonNull(restaurantId);
        Objects.requireNonNull(languageCode);

        List<Menu> menus = menuRepository.getMenus(restaurantId, languageCode);
        logger.debug("Found {} menus for restaurantId={}, language={}", menus.size(), restaurantId, languageCode);
        return menus;
    }

    public Menu getMenu(Long restaurantId, Long menuId) {
        Objects.requireNonNull(restaurantId);
        Objects.requireNonNull(menuId);

        Menu menu = menuRepository.getMenuById(restaurantId, menuId);
        logger.debug("Found menu for restaurantId={}, menuId={}", restaurantId, menuId);
        return menu;
    }

}