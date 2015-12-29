package sgf.gateway.web.controllers.dataset.responsibleParty;

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
import sgf.gateway.service.security.AuthorizationUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ResponsiblePartyOrderController {

    private static final String ORDER_DATASET_RESPONSIBLE_PARTY_FORM = "/dataset/responsibleParty/orderResponsibleParties";
    private static final String ORDER_PROJECT_RESPONSIBLE_PARTY_FORM = "/project/responsibleParty/orderResponsibleParties";

    private final CadisDatasetService cadisDatasetService;
    private final AuthorizationUtils authorizationUtils;

    public ResponsiblePartyOrderController(CadisDatasetService cadisDatasetService, AuthorizationUtils authorizationUtils) {

        this.cadisDatasetService = cadisDatasetService;
        this.authorizationUtils = authorizationUtils;
    }

    @ModelAttribute("command")
    public ResponsiblePartyOrderCommand setupCommand(@PathVariable("dataset") Dataset dataset) {

        List<ResponsibleParty> responsibleParties = (List<ResponsibleParty>) dataset.getDescriptiveMetadata().getResponsibleParties();

        ResponsiblePartyOrderCommand command = new ResponsiblePartyOrderCommand(dataset, responsibleParties);

        return command;
    }

    @RequestMapping(value = "/dataset/{dataset}/responsibleParty/form/order.html", method = RequestMethod.GET)
    public ModelAndView getAddDatasetResponsiblePartyForm(@PathVariable("dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        return new ModelAndView(ORDER_DATASET_RESPONSIBLE_PARTY_FORM);
    }

    @RequestMapping(value = "/project/{dataset}/responsibleParty/form/order.html", method = RequestMethod.GET)
    public ModelAndView getAddProjectResponsiblePartyForm(@PathVariable("dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        return new ModelAndView(ORDER_PROJECT_RESPONSIBLE_PARTY_FORM);
    }

    @RequestMapping(value = "/dataset/{dataset}/orderResponsibleParties.html", method = RequestMethod.POST)
    public ModelAndView reorderResponsiblePartyForDatsets(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") ResponsiblePartyOrderCommand command,
                                                          BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(ORDER_DATASET_RESPONSIBLE_PARTY_FORM);

        } else {

            List<ResponsibleParty> parties = command.getResponsibleParties();

            this.cadisDatasetService.reorderResponsibleParties(dataset, parties);

            redirectAttributes.addFlashAttribute("successMessage", "Responsible Parties re-ordered.");

            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/responsibleParty.html");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/project/{dataset}/orderResponsibleParties.html", method = RequestMethod.POST)
    public ModelAndView reorderResponsiblePartyForProjects(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") ResponsiblePartyOrderCommand command,
                                                           BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(ORDER_PROJECT_RESPONSIBLE_PARTY_FORM);

        } else {

            List<ResponsibleParty> parties = command.getResponsibleParties();

            this.cadisDatasetService.reorderResponsibleParties(dataset, parties);

            redirectAttributes.addFlashAttribute("successMessage", "Responsible Parties re-ordered.");

            modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + "/responsibleParty.html");
        }

        return modelAndView;
    }

}
