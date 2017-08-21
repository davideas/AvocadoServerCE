package eu.davidea.avocadoserver.boundary.rest.api.menu;

import eu.davidea.avocadoserver.boundary.helpers.EntityToDtoHelper;
import eu.davidea.avocadoserver.business.menu.GetMenuUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
