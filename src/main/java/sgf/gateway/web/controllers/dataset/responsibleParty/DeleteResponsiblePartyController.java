package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class DeleteResponsiblePartyController {

    private static final String DELETE_RESPONSIBLE_PARTY_FORM = "/dataset/responsibleParty/deleteResponsiblePartyForm";
    private static final String DELETE_PROJECT_RESPONSIBLE_PARTY_FORM = "/project/responsibleParty/deleteResponsiblePartyForm";
    private String viewName = DELETE_RESPONSIBLE_PARTY_FORM;
    private final CadisDatasetService cadisDatasetService;
    private final AuthorizationUtils authorizationUtils;

    public DeleteResponsiblePartyController(CadisDatasetService cadisDatasetService, AuthorizationUtils authorizationUtils) {

        this.cadisDatasetService = cadisDatasetService;
        this.authorizationUtils = authorizationUtils;
    }

    @ModelAttribute("command")
    public DeleteResponsiblePartyCommand setupCommand(@PathVariable("dataset") Dataset dataset, @PathVariable("responsiblePartyId") UUID responsiblePartyId) {

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        if (responsibleParty == null) {

            throw new ObjectNotFoundException(responsiblePartyId);
        }

        DeleteResponsiblePartyCommand command = new DeleteResponsiblePartyCommand(dataset, responsibleParty);

        return command;
    }

    @RequestMapping(value = "/dataset/{dataset}/responsibleParty/{responsiblePartyId}/form/delete.html", method = RequestMethod.GET)
    public ModelAndView getDeleteResponsiblePartyForm(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "responsiblePartyId") UUID responsiblePartyId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        ModelAndView modelAndView = new ModelAndView(DELETE_RESPONSIBLE_PARTY_FORM);
        modelAndView.addObject("responsibleParty", responsibleParty);

        return modelAndView;
    }

    @RequestMapping(value = "/project/{dataset}/responsibleParty/{responsiblePartyId}/form/delete.html", method = RequestMethod.GET)
    public ModelAndView getProjectDeleteResponsiblePartyForm(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "responsiblePartyId") UUID responsiblePartyId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        ModelAndView modelAndView = new ModelAndView(DELETE_PROJECT_RESPONSIBLE_PARTY_FORM);
        modelAndView.addObject("responsibleParty", responsibleParty);

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/responsibleParty/{responsiblePartyId}", "/project/{dataset}/responsibleParty/{responsiblePartyId}"}, method = RequestMethod.DELETE)
    public ModelAndView deleteResponsibleParty(@PathVariable(value = "dataset") Dataset dataset, @PathVariable(value = "responsiblePartyId") UUID responsiblePartyId,
                                               @ModelAttribute("command") @Valid DeleteResponsiblePartyCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                               HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        String viewPathOption = getViewPathFromRequestUrl(request); //"project" or "dataset"

        if (viewPathOption.equals("project")) {
            this.viewName = DELETE_PROJECT_RESPONSIBLE_PARTY_FORM;
        }

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(this.viewName);

        } else {

            this.cadisDatasetService.deleteResponsibleParty(command);

            redirectAttributes.addFlashAttribute("successMessage", "Responsible Party deleted.");

            modelAndView = new ModelAndView("redirect:/" + viewPathOption + "/" + dataset.getShortName() + "/responsibleParty.html");
        }

        return modelAndView;
    }

    // To avoid duplicating the method for the "project" vs "dataset" path, determine path so decide which url to follow next
    private String getViewPathFromRequestUrl(HttpServletRequest request) {

        String mappingValue = request.getServletPath();

        // "dataset" or "project"
        String[] projectOrDatasetUrlPathway = mappingValue.split("/");

        return projectOrDatasetUrlPathway[1];
    }
}
