package sgf.gateway.web.controllers.dataset.relatedLink;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.service.metadata.RelatedLinkService;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.validation.Valid;
import java.util.Collection;


/**
 * Controller for form to allow adding a Link to a dataset metadata.
 * <p/>
 * Edit will be a distinct operation with it's own controller.
 */
@Controller
public class RelatedLinkController {

    private RelatedLinkService relatedLinkService;
    private final AuthorizationUtils authorizationUtils;

    public RelatedLinkController(RelatedLinkService relatedLinkService, AuthorizationUtils authorizationUtils) {

        this.relatedLinkService = relatedLinkService;
        this.authorizationUtils = authorizationUtils;
    }

    // Set up form backing object for ADD
    @ModelAttribute("command")
    public RelatedLinkCommand setupCommand(@PathVariable(value = "dataset") Dataset dataset) {

        //Create form backing object
        RelatedLinkCommand command = new RelatedLinkCommand(dataset);

        return command;
    }


    // Show initial ADD form
    @RequestMapping(value = "dataset/{dataset}/relatedLink/form/add.html", method = RequestMethod.GET)
    public ModelAndView showForm(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        //this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("relatedLink/addLinkForm");

        return modelAndView;
    }

    // Submit ADD form
    @RequestMapping(value = "dataset/{dataset}/relatedLink/form/add", method = RequestMethod.POST)
    public ModelAndView processSubmit(@ModelAttribute("command") @Valid RelatedLinkCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String resultView;

        Dataset dataset = command.getDataset();

        this.authorizationUtils.authorizeForWrite(dataset);

        if (bindingResult.hasErrors()) {

            resultView = "relatedLink/addLinkForm";

        } else {

            resultView = this.getSuccessView(dataset);

            this.relatedLinkService.addRelatedLinkToDataset(dataset, command);

            redirectAttributes.addFlashAttribute("successMessage", "Link created.");

        }

        return new ModelAndView(resultView);
    }

    // Show list
    @RequestMapping(value = "/dataset/{dataset}/relatedLink.html", method = RequestMethod.GET)
    public ModelAndView showLinks(@PathVariable(value = "dataset") Dataset dataset) {

        Collection<RelatedLink> links = dataset.getDescriptiveMetadata().getRelatedLinks();

        ModelAndView modelAndView = new ModelAndView("/relatedLink/relatedLink");
        modelAndView.addObject("relatedLinks", links);

        return modelAndView;
    }


    protected String getSuccessView(Dataset dataset) {

        return "redirect:" + "/dataset/" + dataset.getShortName() + "/relatedLink.html";
    }

}
