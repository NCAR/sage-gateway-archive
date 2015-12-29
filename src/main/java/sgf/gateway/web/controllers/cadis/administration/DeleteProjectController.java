package sgf.gateway.web.controllers.cadis.administration;

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
public class DeleteProjectController {

    private final MetadataService metadataService;
    private final AuthorizationUtils authorizationUtils;

    public DeleteProjectController(MetadataService metadataService, AuthorizationUtils authorizationUtils) {
        super();
        this.metadataService = metadataService;
        this.authorizationUtils = authorizationUtils;
    }

    @RequestMapping(value = "/project/{dataset}/form/confirmDelete.html", method = RequestMethod.GET)
    ModelAndView showForm(@PathVariable(value = "dataset") Dataset dataset) {
        return new ModelAndView("/cadis/project/deleteConfirmation");
    }

    @RequestMapping(value = "/project/{dataset}/form/confirmDelete.html", method = RequestMethod.DELETE)
    ModelAndView deleteDataset(@PathVariable(value = "dataset") Dataset dataset,
                               @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (deleteConfirmed) {

            this.metadataService.deleteDataset(dataset);

            redirectAttributes.addFlashAttribute("successMessage", "Project deleted.");

            modelAndView = new ModelAndView("redirect:/home.html");

        } else {

            modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }
}
