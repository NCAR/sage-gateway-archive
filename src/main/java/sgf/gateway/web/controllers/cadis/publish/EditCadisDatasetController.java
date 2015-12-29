package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class EditCadisDatasetController extends AbstractCadisDatasetController {

    private String formView = "/cadis/publish/createDatasetForm";

    private CadisDatasetService cadisDatasetService;
    private DatasetToCommandConverter datasetToCommandConverter;


    public EditCadisDatasetController(CadisDatasetService cadisDatasetService, DatasetRepository datasetRepository, TopicRepository topicRepository,
                                      MetadataRepository metadataRepository, PlatformTypeRepository platformTypeRepository, InstrumentKeywordRepository instrumentKeywordRepository,
                                      AuthorizationUtils authorizationUtils,
                                      DatasetToCommandConverter datasetToCommandConverter) {

        super(datasetRepository, metadataRepository, instrumentKeywordRepository, platformTypeRepository, topicRepository, authorizationUtils);

        this.cadisDatasetService = cadisDatasetService;
        this.datasetToCommandConverter = datasetToCommandConverter;

    }

    // Set up form backing object
    @ModelAttribute("command")
    public CadisDatasetCommand setupCommand() {

        //Create form backing object
        CadisDatasetCommand command = new CadisDatasetCommand();

        return command;
    }

    /* Reference Data */
    @ModelAttribute("state")
    public String getState() {

        return "edit";
    }

    /*
     *  Show initial form
     */
    @RequestMapping(value = "/dataset/{dataset}/editDatasetForm.html", method = RequestMethod.GET)
    public ModelAndView showForm(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") CadisDatasetCommand command) throws Exception {

        // If brokered dataset, can't edit it, bail out
        if (dataset.isBrokered()) {

            // TODO:  This works but is pretty rustic.  Perhaps throw a special Runtime exception e.g. DatasetNotEditableException (see ProjectNotFoundException)
            ModelAndView errorMav = new ModelAndView("/cadis/common/error");
            errorMav.addObject("errorText", "The dataset " + dataset.getShortName() + " is brokered and therefore not editable.");
            return errorMav;
        }

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView(this.formView);

        this.datasetToCommandConverter.convert(dataset, command);

        return modelAndView;
    }

    /*
     *  Submit form
     */
    @RequestMapping(value = "/dataset/{dataset}/editDatasetForm.html", method = RequestMethod.POST)
    public ModelAndView processSubmit(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") @Valid CadisDatasetCommand command,
                                      BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {

        // If brokered dataset, can't edit it, bail out
        if (dataset.isBrokered()) {

            // TODO:  This works but is pretty rustic.  Perhaps throw a special Runtime exception e.g. DatasetNotEditableException (see ProjectNotFoundException)
            ModelAndView errorMav = new ModelAndView("/cadis/common/error");
            errorMav.addObject("errorText", "The dataset " + dataset.getShortName() + " is brokered and therefore not editable.");
            return errorMav;
        }

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            String resultView = formView;

            modelAndView.setViewName(resultView);

        } else {

            cadisDatasetService.updateDataset(dataset.getShortName(), command);

            redirectAttributes.addFlashAttribute("successMessage", "Dataset updated.");

            String path = request.getContextPath();

            String editResponsiblePartyUrl = createResponsiblePartyPageUrl(dataset, path, "Add/Edit Responsible Parties (Authors, PIs, Contacts, etc.)");
            String fileUploadUrl = createFileUploadPageUrl(dataset, path, "Upload data files");

            redirectAttributes.addFlashAttribute("infoMessage", "Remember to " + editResponsiblePartyUrl + " and " + fileUploadUrl + ".");

            RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + ".html", true);
            redirectView.setExposeModelAttributes(false);
            redirectView.setExposePathVariables(false);
            modelAndView.setView(redirectView);
        }

        return modelAndView;
    }

    private String createResponsiblePartyPageUrl(Dataset dataset, String path, String linkText) {
        String editUrl = "<a href=\"" + path + "/dataset/" + dataset.getShortName() + "/responsibleParty.html\" />Add/Edit Responsible Parties (Authors, PIs, Contacts, etc.)</a> ";
        return editUrl;
    }

    private String createFileUploadPageUrl(Dataset dataset, String path, String linkText) {
        String editUrl = "<a href=\"" + path + "/dataset/" + dataset.getShortName() + "/editfiles.html\" />Upload Data Files</a>";
        return editUrl;
    }
}
