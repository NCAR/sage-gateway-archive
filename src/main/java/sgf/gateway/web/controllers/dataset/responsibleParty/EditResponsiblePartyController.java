package sgf.gateway.web.controllers.dataset.responsibleParty;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.dao.metadata.ResponsiblePartyRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.PrincipalInvestigatorWithData;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.metadata.ObjectNotFoundException;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EditResponsiblePartyController {

    private static final String EDIT_RESPONSIBLE_PARTY_FORM = "dataset/responsibleParty/editResponsiblePartyForm";
    private static final String EDIT_PROJECT_RESPONSIBLE_PARTY_FORM = "project/responsibleParty/editResponsiblePartyForm";
    private final CadisDatasetService cadisDatasetService;
    private final AuthorizationUtils authorizationUtils;
    String viewName = EDIT_RESPONSIBLE_PARTY_FORM;
    private ResponsiblePartyRepository responsiblePartyRepository;

    public EditResponsiblePartyController(CadisDatasetService cadisDatasetService, AuthorizationUtils authorizationUtils,
                                          ResponsiblePartyRepository responsiblePartyRepository) {

        this.cadisDatasetService = cadisDatasetService;
        this.authorizationUtils = authorizationUtils;
        this.responsiblePartyRepository = responsiblePartyRepository;
    }

    @ModelAttribute("command")
    public ResponsiblePartyCommand setupCommand(@PathVariable("dataset") Dataset dataset, @PathVariable("responsiblePartyId") UUID responsiblePartyId) {

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        if (responsibleParty == null) {

            throw new ObjectNotFoundException(responsiblePartyId);
        }

        ResponsiblePartyCommand command = new ResponsiblePartyCommand(dataset, responsibleParty);

        return command;
    }

    @ModelAttribute("responsiblePartyRoles")
    public ResponsiblePartyRole[] setupResponsiblePartyRoles() {

        ResponsiblePartyRole[] roles = ResponsiblePartyRole.values();

        return roles;
    }

    @ModelAttribute("existingResponsibleParties")
    public List<PrincipalInvestigatorWithData> setupExistingResponsibleParties() {

        List<PrincipalInvestigatorWithData> existingResponsibleParties = responsiblePartyRepository.findAllResponsiblePartiesOrdered();

        return existingResponsibleParties;
    }

    // Create comma-separated list of names for javascript jquery autocomplete
    @ModelAttribute("existingResponsiblePartiesString")
    public String setupExistingResponsiblePartiesString() {

        List<PrincipalInvestigatorWithData> existingResponsibleParties = responsiblePartyRepository.findAllResponsiblePartiesOrdered();
        List<String> names = new ArrayList<String>();

        for (PrincipalInvestigatorWithData person : existingResponsibleParties) {
            names.add("\'" + (person.getFullName()).replace("'", "\\'") + "\'");  //TODO: remove any commas
        }

        //StringUtils.join(java.lang.Iterable,char)  //Apache commons

        String theString = StringUtils.collectionToDelimitedString(names, ", ");

        return theString;
    }

    @RequestMapping(value = "/dataset/{dataset}/responsibleParty/{responsiblePartyId}/form/edit.html", method = RequestMethod.GET)
    public ModelAndView getEditResponsiblePartyForm(@PathVariable("dataset") Dataset dataset, @PathVariable("responsiblePartyId") UUID responsiblePartyId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        ModelAndView modelAndView = new ModelAndView(EDIT_RESPONSIBLE_PARTY_FORM);
        modelAndView.addObject("responsibleParty", responsibleParty);

        return modelAndView;
    }


    @RequestMapping(value = "/project/{dataset}/responsibleParty/{responsiblePartyId}/form/edit.html", method = RequestMethod.GET)
    public ModelAndView getEditProjectResponsiblePartyForm(@PathVariable("dataset") Dataset dataset, @PathVariable("responsiblePartyId") UUID responsiblePartyId) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ResponsibleParty responsibleParty = dataset.getDescriptiveMetadata().getResponsibleParty(responsiblePartyId);

        ModelAndView modelAndView = new ModelAndView(EDIT_PROJECT_RESPONSIBLE_PARTY_FORM);
        modelAndView.addObject("responsibleParty", responsibleParty);

        return modelAndView;
    }


    @RequestMapping(value = {"/project/{dataset}/responsibleParty/{responsiblePartyId}.html", "/dataset/{dataset}/responsibleParty/{responsiblePartyId}.html"}, method = RequestMethod.POST)
    public ModelAndView editProjectResponsibleParty(@PathVariable(value = "dataset") Dataset dataset, @PathVariable("responsiblePartyId") UUID responsiblePartyId,
                                                    @ModelAttribute("command") @Valid ResponsiblePartyCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                                    HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        String viewPathOption = getViewPathFromRequestUrl(request); //"project" or "dataset"

        if (viewPathOption.equals("project")) {
            this.viewName = EDIT_PROJECT_RESPONSIBLE_PARTY_FORM;
        }

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(this.viewName);

        } else {

            this.cadisDatasetService.updateResponsibleParty(command);

            redirectAttributes.addFlashAttribute("successMessage", "Responsible Party updated.");

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
