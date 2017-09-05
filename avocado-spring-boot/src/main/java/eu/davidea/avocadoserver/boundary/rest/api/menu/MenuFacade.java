package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.business.menu.GetMenuUseCase;
import eu.davidea.avocadoserver.business.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MenuFacade {

    private final static Logger logger = LoggerFactory.getLogger(MenuFacade.class);

    private GetMenuUseCase getMenuUseCase;

    @Autowired
    public MenuFacade(GetMenuUseCase getMenuUseCase) {
        this.getMenuUseCase = getMenuUseCase;
    }

    @Transactional(readOnly = true)
    public MenuDTO getMenu(Long restaurantId, Long menuId) {
        Objects.requireNonNull(restaurantId);
        Objects.requireNonNull(menuId);

        Menu menu = getMenuUseCase.getMenu(restaurantId, menuId);
        return MenuDTO.toDto(menu);
    }
}
