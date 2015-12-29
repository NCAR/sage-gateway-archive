package sgf.gateway.web.controllers.publishing.thredds;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.security.User;
import sgf.gateway.publishing.thredds.ThreddsDatasetDetails;
import sgf.gateway.publishing.thredds.ThreddsPublishingEvent;
import sgf.gateway.publishing.thredds.impl.ThreddsDatasetDetailsImpl;
import sgf.gateway.service.security.RuntimeUserService;

import javax.validation.Valid;
import java.net.URI;

@Controller
public class PublishThreddsCatalogController implements ApplicationEventPublisherAware {

    private final RuntimeUserService runtimeUserService;

    private ApplicationEventPublisher applicationEventPublisher;

    @ModelAttribute("command")
    public PublishThreddsCatalogCommand setupCommand() {

        PublishThreddsCatalogCommand command = new PublishThreddsCatalogCommand();

        return command;
    }

    public PublishThreddsCatalogController(RuntimeUserService runtimeUserService) {

        this.runtimeUserService = runtimeUserService;
    }

    @RequestMapping(value = "/publish/publishThreddsCatalog.html", method = RequestMethod.GET)
    public String getForm() {

        return "publish/publishThreddsCatalog";
    }

    @RequestMapping(value = "/publish/publishThreddsCatalog.html", method = RequestMethod.POST)
    public ModelAndView onSubmit(@Valid @ModelAttribute("command") PublishThreddsCatalogCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView("publish/publishThreddsCatalog");

        } else {

            this.publishThreddsCatalog(command);

            redirectAttributes.addFlashAttribute("successMessage", "The THREDDS Catalog has been placed into the publishing queue and should available shortly.");

            modelAndView = new ModelAndView("redirect:/root/index.htm");
        }

        return modelAndView;
    }

    protected void publishThreddsCatalog(PublishThreddsCatalogCommand command) {

        ThreddsDatasetDetails datasetDetails = getDatasetDetailsFromCommand(command);

        ThreddsPublishingEvent event = new ThreddsPublishingEvent(this, datasetDetails);

        this.applicationEventPublisher.publishEvent(event);
    }

    private ThreddsDatasetDetails getDatasetDetailsFromCommand(PublishThreddsCatalogCommand command) {

        User user = this.runtimeUserService.getUser();

        URI catalogUri = getThreddsCatalogURI(command);

        String parentDatasetIdentifier = command.getParentDatasetIdentifier();

        ThreddsDatasetDetails datasetDetails = new ThreddsDatasetDetailsImpl(catalogUri, parentDatasetIdentifier, user.getIdentifier());

        return datasetDetails;
    }

    protected URI getThreddsCatalogURI(PublishThreddsCatalogCommand command) {

        URI catalogURI = URI.create(command.getThreddsCatalogURI());

        return catalogURI;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
