package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.boundary.helpers.EntityToDtoHelper;
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
    private EntityToDtoHelper dtoHelper;

    @Autowired
    public MenuFacade(GetMenuUseCase getMenuUseCase, EntityToDtoHelper dtoHelper) {
        this.getMenuUseCase = getMenuUseCase;
        this.dtoHelper = dtoHelper;
    }

    @Transactional(readOnly = true)
    public MenuDTO getMenu(Long menuId, String languageCode) {
        Objects.requireNonNull(menuId);
        if (languageCode == null) {
            languageCode = "en-UK";
        }

        Menu menu = getMenuUseCase.getMenu(menuId, languageCode);
        return dtoHelper.toDto(menu);
    }

}
