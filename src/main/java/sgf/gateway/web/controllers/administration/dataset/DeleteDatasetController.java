package sgf.gateway.web.controllers.administration.dataset;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.MetadataService;
import sgf.gateway.service.security.AuthorizationUtils;

@Controller
public class DeleteDatasetController {

    private final MetadataService metadataService;
    private final AuthorizationUtils authorizationUtils;
    private final String redirectView;

    public DeleteDatasetController(MetadataService metadataService, AuthorizationUtils authorizationUtils, String redirectView) {

        this.metadataService = metadataService;
        this.authorizationUtils = authorizationUtils;
        this.redirectView = redirectView;
    }

    @RequestMapping(value = "/dataset/{dataset}/form/confirmDelete.html", method = RequestMethod.GET)
    ModelAndView showForm(@PathVariable(value = "dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        return new ModelAndView("/administration/dataset/deleteConfirmation");
    }

    @RequestMapping(value = "/dataset/{dataset}/form/confirmDelete.html", method = RequestMethod.DELETE)
    ModelAndView deleteDataset(@PathVariable(value = "dataset") Dataset dataset,
                               @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (deleteConfirmed) {

            this.metadataService.deleteDataset(dataset);

            redirectAttributes.addFlashAttribute("successMessage", "Dataset deleted.");

            modelAndView = new ModelAndView(this.redirectView);

        } else {

            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }
}
