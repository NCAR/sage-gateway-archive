package sgf.gateway.web.controllers.cadis.publish;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.web.controllers.cadis.publish.command.CadisMoveDatasetCommand;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CadisMoveDatasetController {

    private final AuthorizationUtils authorizationUtils;
    private CadisDatasetService cadisDatasetService;

    Set<Dataset> descendantDatasets = new HashSet<Dataset>();


    public CadisMoveDatasetController(AuthorizationUtils authorizationUtils, CadisDatasetService cadisDatasetService) {

        this.authorizationUtils = authorizationUtils;
        this.cadisDatasetService = cadisDatasetService;
    }

    @ModelAttribute("command")
    public CadisMoveDatasetCommand setupCommand() {

        return new CadisMoveDatasetCommand();
    }

    /**
     * Show form to select enclosing container.
     */
    @RequestMapping(value = "/dataset/{dataset}/moveDataset.html", method = RequestMethod.GET)
    public ModelAndView showSelectContainerForm(@PathVariable(value = "dataset") Dataset datasetToMove, @ModelAttribute("command") CadisMoveDatasetCommand command) throws Exception {

        this.authorizationUtils.authorizeForWrite(datasetToMove);

        Dataset rootDataset = datasetToMove.getRootParentDataset();  // top of tree (project)

        command.setDatasetToMoveShortName(datasetToMove.getShortName());

        ModelAndView modelAndView = new ModelAndView("/cadis/publish/moveDatasetForm");

        modelAndView.addObject("datasetToMove", datasetToMove);
        modelAndView.addObject("rootDataset", rootDataset);

        this.descendantDatasets.clear();
        setDescendantDatasets(datasetToMove);  // Sets field value due to recursion

        modelAndView.addObject("descendants", descendantDatasets);

        return modelAndView;
    }

    /*
     * Create set of descendants of the datasetToMove so we can tell the view which Datasets are potential new parents and which ineligible.
     */
    protected void setDescendantDatasets(Dataset topNode) {

        Collection<Dataset> children = topNode.getDirectlyNestedDatasets();

        for (Dataset nested : children) {

            this.descendantDatasets.add(nested);

//			System.out.println("Added Nested ---" + nested.getShortName());

            setDescendantDatasets(nested);
        }
    }

    /*
     *  Confirm move dataset
     */
    @RequestMapping(value = "/dataset/{dataset}/moveDatasetConfirmation.html", params = "dataset_to_move_name", method = RequestMethod.GET)
    public ModelAndView showConfirmationForm(@PathVariable(value = "dataset") Dataset parentDataset, @RequestParam(value = "dataset_to_move_name") Dataset currentDataset) throws Exception {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView("/cadis/publish/moveDatasetConfirmation");

        modelAndView.addObject("parentDataset", parentDataset);
        modelAndView.addObject("currentDataset", currentDataset);

        return modelAndView;
    }

    @RequestMapping(value = "/dataset/{dataset}/setParent/{parentDataset}.html", method = RequestMethod.POST)
    public ModelAndView processWizardSubmit(@PathVariable(value = "dataset") Dataset dataset,
                                            @PathVariable(value = "parentDataset") Dataset parentDataset,
                                            @RequestParam("moveConfirmed") boolean moveConfirmed,
                                            @ModelAttribute("command") @Valid CadisMoveDatasetCommand command,
                                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(parentDataset);

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {

            modelAndView.setViewName("/cadis/publish/moveDatasetForm");

        } else {

            if (moveConfirmed) {

                // this.cadisDatasetService.moveDataset(command);
                this.cadisDatasetService.moveDataset(dataset, parentDataset);

                redirectAttributes.addFlashAttribute("successMessage", "Dataset " + dataset.getTitle() + " moved to " + parentDataset.getTitle());

                RedirectView redirectView = new RedirectView("/dataset/" + dataset.getShortName() + "/hierarchy.html", true);
                redirectView.setExposeModelAttributes(false);
                redirectView.setExposePathVariables(false);
                modelAndView.setView(redirectView);
            } else {
                modelAndView = new ModelAndView("redirect:dataset/" + dataset.getShortName() + "/moveDataset.html");
            }
        }


        return modelAndView;
    }

}
