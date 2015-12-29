package sgf.gateway.web.controllers.dataset.file;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.security.AuthorizationUtils;

import javax.validation.Valid;

@Controller
public class BulkFileUploadController {

    private final LogicalFileService logicalFileService;
    private final AuthorizationUtils authorizationUtils;

    public BulkFileUploadController(final LogicalFileService logicalFileService, final AuthorizationUtils authorizationUtils) {

        this.logicalFileService = logicalFileService;
        this.authorizationUtils = authorizationUtils;
    }

    @ModelAttribute("command")
    public FileUploadCommand setupCommand(@PathVariable(value = "dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        //Create form backing object
        FileUploadCommand command = new FileUploadCommand(dataset);

        return command;
    }

    @RequestMapping(value = "/dataset/{dataset}/bulkFileUploadForm.html", method = RequestMethod.GET)
    public String setupForm() {

        return "dataset/bulkFileUploadForm";
    }

    @RequestMapping(value = "/dataset/{dataset}/bulkFileUploadForm", method = RequestMethod.POST)
    public String processSubmit(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") @Valid FileUploadCommand command, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String resultView;

        this.authorizationUtils.authorizeForWrite(dataset);

        if (bindingResult.hasErrors()) {

            resultView = "dataset/bulkFileUploadForm";

        } else {

            this.logicalFileService.transferFilesToDataset(command);

            resultView = "redirect:/dataset/" + dataset.getShortName() + "/editfile.html";

            redirectAttributes.addFlashAttribute("successMessage", "All files uploaded successfully.");
        }

        return resultView;
    }
}
