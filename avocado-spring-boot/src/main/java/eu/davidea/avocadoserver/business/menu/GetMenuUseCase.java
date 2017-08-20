package eu.davidea.avocadoserver.business.menu;

import eu.davidea.avocadoserver.infrastructure.statistics.QueryStatsLogger;
import eu.davidea.avocadoserver.persistence.mybatis.repositories.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

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
    private QueryStatsLogger queryStatsLogger;

    @Autowired
    public GetMenuUseCase(MenuRepository menuRepository,
                          QueryStatsLogger queryStatsLogger) {
        this.menuRepository = menuRepository;
        this.queryStatsLogger = queryStatsLogger;
    }

    public List<Menu> getMenus(Long restaurantId, String languageCode) {
        Objects.requireNonNull(restaurantId);
        Objects.requireNonNull(languageCode);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getMenus");

        try {
            List<Menu> menus = menuRepository.getMenus(restaurantId, languageCode);
            logger.debug("Found {} menus for restaurantId={}, language={}", menus.size(), restaurantId, languageCode);
            return menus;

        } finally {
            stopWatch.stop();
            queryStatsLogger.logQueryStats(stopWatch);
        }
    }

    public Menu getMenu(Long menuId, String languageCode) {
        Objects.requireNonNull(menuId);
        Objects.requireNonNull(languageCode);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("getMenu");

        try {
            Menu menu = menuRepository.getMenu(menuId, languageCode);
            logger.debug("Found {} menu, language={}", menu.getTitle(), languageCode);
            return menu;

        } finally {
            stopWatch.stop();
            queryStatsLogger.logQueryStats(stopWatch);
        }
    }

}