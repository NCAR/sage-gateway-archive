package sgf.gateway.web.controllers.administration;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sgf.gateway.integration.thredds.eolcatalogs.EOLCatalogPublishEvent;

@Controller
public class EOLCatalogJobController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public EOLCatalogJobController() {
        super();
    }

    @RequestMapping(value = "/admin/catalogJob.html", method = RequestMethod.GET)
    public String runCatalogJob() {
        applicationEventPublisher.publishEvent(new EOLCatalogPublishEvent(this));
        return "cadis/root/index";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
