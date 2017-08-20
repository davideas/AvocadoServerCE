package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.menu.Menu;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("restaurantRepository")
public class MenuRepository {

    private final MenuMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    MenuRepository(MenuMapper mapper) {
        this.mapper = mapper;
    }

    public List<Menu> getMenus(Long restaurantId, String languageCode) {
        return mapper.getMenus(restaurantId, languageCode);
    }

    public Menu getMenu(Long menuId, String languageCode) {
        return mapper.getMenu(menuId, languageCode);
    }

}