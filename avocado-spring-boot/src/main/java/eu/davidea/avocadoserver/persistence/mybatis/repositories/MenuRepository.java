package eu.davidea.avocadoserver.persistence.mybatis.repositories;

import eu.davidea.avocadoserver.business.audit.LogQueryStats;
import eu.davidea.avocadoserver.business.menu.Menu;
import eu.davidea.avocadoserver.persistence.mybatis.mappers.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Davide
 * @since 17/08/2016
 */
@Repository("menuRepository")
public class MenuRepository {

    private final MenuMapper mapper;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    MenuRepository(MenuMapper mapper) {
        this.mapper = mapper;
    }

    @LogQueryStats
    public List<Menu> getMenus(Long restaurantId, String languageCode) {
        return mapper.getMenus(restaurantId, languageCode);
    }

}