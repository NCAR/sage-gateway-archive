package sgf.gateway.integration.thredds.eolcatalogs;

import org.springframework.context.ApplicationEvent;

public class EOLCatalogPublishEvent extends ApplicationEvent {

    public EOLCatalogPublishEvent(Object source) {
        super(source);
    }
}
