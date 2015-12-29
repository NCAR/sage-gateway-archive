package sgf.gateway.web.controllers.dataset;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.web.controllers.project.command.NewAwardCommand;


/**
 * Controller for form to allow adding an Award (award number) to a dataset.
 * <p/>
 * Edit will be a distinct operation with it's own controller.
 */
@Controller
@RequestMapping(value = {"dataset/{dataset}/awardeditor.html"})
public class DatasetAwardEditorController {

    private String formView = "dataset/award/awardeditor";

    private CadisDatasetService datasetService;
    private Validator validator;

    public DatasetAwardEditorController(Validator validator, CadisDatasetService datasetService) {

        this.validator = validator;
        this.datasetService = datasetService;
    }

    // Set up form backing object
    @ModelAttribute("command")
    public NewAwardCommand setupCommand(@PathVariable(value = "dataset") Dataset dataset) {

        //Create form backing object
        NewAwardCommand command = new NewAwardCommand(dataset);

        return command;
    }

    // Show initial form
    @RequestMapping(method = RequestMethod.GET)
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
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processSubmit(@ModelAttribute("command") NewAwardCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String resultView;

        validator.validate(command, bindingResult);

        if (bindingResult.hasErrors()) {

            resultView = this.formView;

        } else {

            this.datasetService.addAwardToDataset(command.getDataset().getIdentifier(), command.getAwardNumber());
            redirectAttributes.addFlashAttribute("successMessage", "Award Number Added.");

            resultView = "redirect:" + "/dataset/" + command.getDataset().getShortName() + "/awardNumber.html";
        }

        return new ModelAndView(resultView);
    }
}
