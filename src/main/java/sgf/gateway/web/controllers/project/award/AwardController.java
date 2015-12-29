package sgf.gateway.web.controllers.project.award;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.security.AuthorizationUtils;

import java.util.Collection;

@Controller
public class AwardController {

    private final AuthorizationUtils authorizationUtils;
    private final CadisDatasetService datasetService;

    public AwardController(final AuthorizationUtils authorizationUtils, final CadisDatasetService datasetService) {
        this.authorizationUtils = authorizationUtils;
        this.datasetService = datasetService;
    }

    @RequestMapping(value = "/project/{dataset}/awardNumber", method = RequestMethod.GET)
    public ModelAndView getProjectAwards(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/project/award/view-awards");

        Collection<Award> awards = dataset.getAwards();
        modelAndView.addObject("awards", awards);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/awardNumber", method = RequestMethod.GET)
    public ModelAndView getDatasetAwards(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("/dataset/award/view-awards");

        Collection<Award> awards = dataset.getAwards();
        modelAndView.addObject("awards", awards);

        return modelAndView;
    }

    @RequestMapping(value = {"/project/{dataset}/awardNumber/{awardNumber}"}, method = RequestMethod.DELETE)
    public ModelAndView removeAwardNumberFromProject(@PathVariable(value = "dataset") Dataset dataset,
                                                     @PathVariable(value = "awardNumber") String awardNumber,
                                                     @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        if (deleteConfirmed) {
            this.datasetService.deleteAwardFromDataset(dataset.getIdentifier(), awardNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Award Number Deleted.");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + "/awardNumber.html");

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/awardNumber/{awardNumber}"}, method = RequestMethod.DELETE)
    public ModelAndView removeAwardNumberFromDataset(@PathVariable(value = "dataset") Dataset dataset,
                                                     @PathVariable(value = "awardNumber") String awardNumber,
                                                     @RequestParam("deleteConfirmed") boolean deleteConfirmed, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        if (deleteConfirmed) {
            this.datasetService.deleteAwardFromDataset(dataset.getIdentifier(), awardNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Award Number Deleted.");
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + "/awardNumber.html");

        return modelAndView;
    }

    @RequestMapping(value = {"/project/{dataset}/awardNumber/{awardNumber}/form/confirmDelete"})
    public ModelAndView removeAwardNumberFromProjectConfirm(@PathVariable(value = "dataset") Dataset dataset,
                                                            @PathVariable(value = "awardNumber") String awardNumber) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("project/award/confirm-remove-award");
        modelAndView.addObject("awardNumber", awardNumber);

        return modelAndView;
    }

    @RequestMapping(value = {"/dataset/{dataset}/awardNumber/{awardNumber}/form/confirmDelete"})
    public ModelAndView removeAwardNumberFromDatasetConfirm(@PathVariable(value = "dataset") Dataset dataset,
                                                            @PathVariable(value = "awardNumber") String awardNumber) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView = new ModelAndView("dataset/award/confirm-remove-award");
        modelAndView.addObject("awardNumber", awardNumber);

        return modelAndView;
    }
}
