package sgf.gateway.web.controllers.observing;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.service.metadata.PlatformTypeService;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for form to allow adding a PlatformType.
 */
@Controller
public class PlatformTypeController {

    private String formView = "observing/newPlatformTypeForm";
    private String successView = "platform";

    private PlatformTypeService platformTypeService;
    private PlatformTypeRepository platformTypeRepository;

    public PlatformTypeController(PlatformTypeService platformTypeService, PlatformTypeRepository platformTypeRepository) {

        this.platformTypeService = platformTypeService;
        this.platformTypeRepository = platformTypeRepository;
    }

    @ModelAttribute("command")
    public PlatformTypeCommand setupCommand() {

        PlatformTypeCommand command = new PlatformTypeCommand();

        return command;
    }

    // Reference Data
    @ModelAttribute("platformTypes")
    List<PlatformType> getPlatformTypes() {

        return this.platformTypeRepository.getAll();
    }

    @RequestMapping(value = "/observing/platform", method = RequestMethod.GET)
    public ModelAndView setupForm() throws Exception {

        // Create ModelAndView
        ModelAndView modelAndView = new ModelAndView(this.formView);

        return modelAndView;
    }

    /**
     * Submit form and perform validation.
     * <p/>
     * Errors - Stores and exposes information about data-binding and validation errors for immediately preceding command object.
     * OR
     * BindingResult - Subinterface of Errors. Validation results for the immediately preceding command object.  E.g type conversion, missing fields.
     * Extends the interface for error registration capabilities, allowing for a Validator to be applied, and adds binding-specific analysis and model building. (getModel())
     * e.g. mav.getModel().putAll(bindingResult.getModel());
     * The result of the validation can be accessed using the BindingResult method parameter.
     * <p/>
     * The Errors or BindingResult parameters have to follow the model object that is being bound immediately as the method signature might have more that one model object and Spring will create a separate BindingResult instance for each of them
     */
    @RequestMapping(value = "observing/platform", method = RequestMethod.POST)
    public ModelAndView processSubmit(@ModelAttribute("command") @Valid PlatformTypeCommand command, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(this.formView);

        } else {

            PlatformType platformType = platformTypeService.addPlatformType(command);

            RedirectView redirectView = new RedirectView(this.successView, true);
            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }
}
