package sgf.gateway.web.controllers.dataset.relatedLink;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.RelatedLinkImpl;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.metadata.RelatedLinkService;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.validation.Valid;
import java.net.URI;


/**
 * Controller for form to allow adding a Link to a dataset metadata.
 * <p/>
 * Edit will be a distinct operation with it's own controller.
 */
@Controller
public class RelatedLinkEditorController {

    private RelatedLinkService relatedLinkService;
    private final AuthorizationUtils authorizationUtils;

    public RelatedLinkEditorController(RelatedLinkService relatedLinkService, AuthorizationUtils authorizationUtils) {

        this.relatedLinkService = relatedLinkService;
        this.authorizationUtils = authorizationUtils;
    }


    // Set up form backing object for ADD or EDIT
    @ModelAttribute("command")
    public RelatedLinkCommand setupCommand(@PathVariable("dataset") Dataset dataset, @PathVariable(value = "relatedLinkId") UUID relatedLinkId) {

        RelatedLinkCommand command;

        if (relatedLinkId == null) { //add

            command = new RelatedLinkCommand(dataset);

        } else {  //edit

            RelatedLink link = dataset.getDescriptiveMetadata().getRelatedLink(relatedLinkId);

            if (link == null) {

                throw new ObjectNotFoundException(relatedLinkId);
            }

            command = new RelatedLinkCommand(dataset, link);

        }

        return command;
    }


    @RequestMapping(value = "/dataset/{dataset}/relatedLink/{relatedLinkId}/form/edit", method = RequestMethod.GET)
    public ModelAndView getEditResponsiblePartyForm(@PathVariable("dataset") Dataset dataset, @PathVariable("relatedLinkId") UUID relatedLinkId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        RelatedLink relatedLink = dataset.getDescriptiveMetadata().getRelatedLink(relatedLinkId);

        ModelAndView modelAndView = new ModelAndView("relatedLink/editLinkForm");

        modelAndView.addObject("relatedLink", relatedLink);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/relatedLink/{relatedLinkId}", method = RequestMethod.POST)
    public ModelAndView postEditResponsibleParty(@PathVariable(value = "dataset") Dataset dataset, @PathVariable("relatedLinkId") UUID relatedLinkId,
                                                 @ModelAttribute("command") @Valid RelatedLinkCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        String resultView;

        if (bindingResult.hasErrors()) {

            resultView = "relatedLink/editLinkForm";

        } else {

            RelatedLink newLink = new RelatedLinkImpl(relatedLinkId, command.getLinkText(), URI.create(command.getLinkUri()));

            this.relatedLinkService.updateRelatedLink(dataset, newLink);

            redirectAttributes.addFlashAttribute("successMessage", "Link updated.");

            resultView = "redirect:" + "/dataset/" + dataset.getShortName() + "/relatedLink.html";  // TODO: Make a var
        }

        return new ModelAndView(resultView);
    }

    //DELETE
    @RequestMapping(value = "/dataset/{dataset}/relatedLink/{relatedLinkId}/form/delete.html", method = RequestMethod.GET)
    public ModelAndView removeLinkFromDatasetConfirm(@PathVariable("dataset") Dataset dataset, @PathVariable("relatedLinkId") UUID relatedLinkId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        RelatedLink relatedLink = dataset.getDescriptiveMetadata().getRelatedLink(relatedLinkId);

        ModelAndView modelAndView = new ModelAndView("/relatedLink/deleteLinkConfirmForm");

        modelAndView.addObject("relatedLink", relatedLink);

        return modelAndView;
    }


    @RequestMapping(value = "/dataset/{dataset}/relatedLink/{relatedLinkId}", method = RequestMethod.DELETE)
    public ModelAndView removeLinkFromDataset(@PathVariable(value = "dataset") final Dataset dataset,
                                              @PathVariable("relatedLinkId") UUID relatedLinkId,
                                              @RequestParam("confirmDelete") boolean confirmDelete, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        if (confirmDelete) {

            RelatedLink relatedLink = dataset.getDescriptiveMetadata().getRelatedLink(relatedLinkId);

            this.relatedLinkService.removeRelatedLinkFromDataset(dataset, relatedLink);

            redirectAttributes.addFlashAttribute("successMessage", "Related Link Removed.");
        }

        String view = "redirect:" + "/dataset/" + dataset.getShortName() + "/relatedLink.html";

        ModelAndView modelAndView = new ModelAndView(view);

        return modelAndView;
    }
}
