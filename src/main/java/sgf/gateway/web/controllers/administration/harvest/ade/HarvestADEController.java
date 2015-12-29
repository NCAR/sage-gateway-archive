package sgf.gateway.web.controllers.administration.harvest.ade;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import sgf.gateway.integration.ade.ADEHarvestEvent;

@Controller
public class HarvestADEController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    public HarvestADEController() {
        super();
    }

    @RequestMapping(value = "/harvest/root/ADEDatasets.html", method = RequestMethod.GET)
    public String harvest() {
        applicationEventPublisher.publishEvent(new ADEHarvestEvent(this));
        return "cadis/root/index";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
