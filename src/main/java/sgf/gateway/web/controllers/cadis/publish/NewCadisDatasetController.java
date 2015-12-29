package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.metadata.*;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.web.controllers.cadis.publish.command.CadisDatasetCommand;
import sgf.gateway.web.controllers.cadis.publish.command.DatasetToCommandConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class NewCadisDatasetController extends AbstractCadisDatasetController {

    private String formView = "/cadis/publish/createDatasetForm";

    private CadisDatasetService cadisDatasetService;
    private DatasetToCommandConverter datasetToCommandConverter;


    public NewCadisDatasetController(CadisDatasetService cadisDatasetService, DatasetRepository datasetRepository, TopicRepository topicRepository,
                                     MetadataRepository metadataRepository, PlatformTypeRepository platformTypeRepository, InstrumentKeywordRepository instrumentKeywordRepository,
                                     AuthorizationUtils authorizationUtils, DatasetToCommandConverter datasetToCommandConverter) {

        super(datasetRepository, metadataRepository, instrumentKeywordRepository, platformTypeRepository, topicRepository, authorizationUtils);

        this.cadisDatasetService = cadisDatasetService;
        this.datasetToCommandConverter = datasetToCommandConverter;
    }

    @ModelAttribute("command")
    public CadisDatasetCommand setupCommand(@PathVariable(value = "dataset") Dataset parentDataset) {

        return new CadisDatasetCommand();
    }

    @ModelAttribute("state")
    public String getState() {

        return "create";
    }

    @RequestMapping(value = "/dataset/{dataset}/createDatasetForm.html", method = RequestMethod.GET)
    public ModelAndView showForm(@PathVariable(value = "dataset") Dataset parentDataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView(this.formView);

        modelAndView.addObject("parentDataset", parentDataset);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/createNewDataset3.html", method = RequestMethod.GET)
    public ModelAndView showWizardForm(@PathVariable(value = "dataset") Dataset parentDataset) throws Exception {

        ModelAndView modelAndView = this.showForm(parentDataset);
        modelAndView.addObject("showWizardComponent", true);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/createDatasetForm.html", params = "template_id", method = RequestMethod.GET)
    public ModelAndView showForm(@PathVariable(value = "dataset") Dataset parentDataset, @RequestParam(value = "template_id") Dataset templateDataset,
                                 @ModelAttribute("command") CadisDatasetCommand command) throws Exception {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView(this.formView);

        modelAndView.addObject("parentDataset", parentDataset);

        this.datasetToCommandConverter.convert(templateDataset, command);

        command.setShortName(null);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/createNewDataset3.html", params = "template_id", method = RequestMethod.GET)
    public ModelAndView showWizardForm(@PathVariable(value = "dataset") Dataset parentDataset, @RequestParam(value = "template_id") Dataset templateDataset,
                                       @ModelAttribute("command") CadisDatasetCommand command) throws Exception {

        ModelAndView modelAndView = this.showForm(parentDataset, templateDataset, command);
        modelAndView.addObject("showWizardComponent", true);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/createDatasetForm", method = RequestMethod.POST)
    public ModelAndView processSubmit(@PathVariable(value = "dataset") Dataset parentDataset, @ModelAttribute("command") @Valid CadisDatasetCommand command,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(this.formView);

        } else {

            Dataset dataset = cadisDatasetService.createDataset(parentDataset, command);

            redirectAttributes.addFlashAttribute("successMessage", "Dataset created.");

            String path = request.getContextPath();
            String editUrl = createResponsiblePartyPageUrl(dataset, path);
            redirectAttributes.addFlashAttribute("infoMessage", "Remember to " + editUrl);

            RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + ".html", true);
            redirectView.setExposeModelAttributes(false);
            redirectView.setExposePathVariables(false);
            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/createNewDataset3.html", method = RequestMethod.POST)
    public ModelAndView processWizardSubmit(@PathVariable(value = "dataset") Dataset parentDataset, @ModelAttribute("command") @Valid CadisDatasetCommand command,
                                            BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName(this.formView);
            modelAndView.addObject("showWizardComponent", true);

        } else {

            Dataset dataset = cadisDatasetService.createDataset(parentDataset, command);

            redirectAttributes.addFlashAttribute("successMessage", "Dataset created.");

            String path = request.getContextPath();
            String editResponsiblePartyUrl = createResponsiblePartyPageUrl(dataset, path);
            String fileUploadUrl = createFileUploadPageUrl(dataset, path, "Upload data files");
            redirectAttributes.addFlashAttribute("infoMessage", "Remember to " + editResponsiblePartyUrl + " and " + fileUploadUrl + ".");

            RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + ".html", true);
            redirectView.setExposeModelAttributes(false);
            redirectView.setExposePathVariables(false);
            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    private String createResponsiblePartyPageUrl(Dataset dataset, String path) {
        String editUrl = "<a href=\"" + path + "/dataset/" + dataset.getShortName() + "/responsibleParty.html\" />Add/Edit Responsible Parties (Authors, PIs, Contacts, etc.)</a>";
        return editUrl;
    }

    private String createFileUploadPageUrl(Dataset dataset, String path, String linkText) {
        String editUrl = "<a href=\"" + path + "/dataset/" + dataset.getShortName() + "/editfiles.html\" />Upload Data Files</a>";
        return editUrl;
    }
}
