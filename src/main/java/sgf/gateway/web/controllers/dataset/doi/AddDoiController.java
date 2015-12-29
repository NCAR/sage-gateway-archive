package sgf.gateway.web.controllers.dataset.doi;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.doi.DoiService;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.validation.Valid;

@Controller
public class AddDoiController {

    private static final String ADD_DOI_FORM = "/dataset/doi/addDoiForm";
    private final DoiService doiService;
    private final AuthorizationUtils authorizationUtils;
    private final String publisher;

    public AddDoiController(DoiService doiService, AuthorizationUtils authorizationUtils, String publisher) {

        this.doiService = doiService;
        this.authorizationUtils = authorizationUtils;
        this.publisher = publisher;
    }

    @ModelAttribute("command")
    public DoiCommand setupCommand(@PathVariable("dataset") Dataset dataset) {

        DoiCommand command = new DoiCommand(dataset, this.publisher);

        return command;
    }

    @RequestMapping(value = "/dataset/{dataset}/doi/form/add.html", method = RequestMethod.GET)
    public ModelAndView getDoiForm(@PathVariable(value = "dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView(ADD_DOI_FORM);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/doi.html", method = RequestMethod.POST)
    public ModelAndView postDoiForm(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") @Valid DoiCommand command, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(ADD_DOI_FORM);

        } else {

            this.doiService.mintDoi(command);

            redirectAttributes.addFlashAttribute("successMessage", "DOI created.");

            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/doi.html");
        }

        return modelAndView;
    }
}
