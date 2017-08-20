package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.business.menu.GetMenuUseCase;
import eu.davidea.avocadoserver.business.menu.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MenuFacade {

    private final static Logger logger = LoggerFactory.getLogger(MenuFacade.class);

    private GetMenuUseCase getMenuUseCase;

    @Autowired
    public MenuFacade(GetMenuUseCase getMenuUseCase) {
        this.getMenuUseCase = getMenuUseCase;
    }

    Menu getMenu(Long menuId, String languageCode) {
        Objects.requireNonNull(menuId);
        Objects.requireNonNull(languageCode);

        return getMenuUseCase.getMenu(menuId, languageCode);
    }

}
