package sgf.gateway.web.controllers.dataset;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisDatasetService;
import sgf.gateway.service.security.AuthorizationUtils;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DatasetOrderController {

    private static final String DATASET_ORDER_DATASETS_VIEW = "/dataset/orderDatasets";
    private static final String PROJECT_ORDER_DATASETS_VIEW = "/project/orderDatasets";

    private final CadisDatasetService cadisDatasetService;
    private final AuthorizationUtils authorizationUtils;

    public DatasetOrderController(CadisDatasetService cadisDatasetService, AuthorizationUtils authorizationUtils) {

        this.cadisDatasetService = cadisDatasetService;
        this.authorizationUtils = authorizationUtils;
    }

    @ModelAttribute("command")
    public DatasetOrderCommand setupCommand(@PathVariable("dataset") Dataset parentDataset) {

        List<Dataset> datasetList = new ArrayList<>(parentDataset.getDirectlyNestedDatasets());

        return new DatasetOrderCommand(parentDataset, datasetList);
    }

    @RequestMapping(value = "/dataset/{dataset}/form/order.html", method = RequestMethod.GET)
    public ModelAndView getDatasetOrderForm(@PathVariable("dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        return new ModelAndView(DATASET_ORDER_DATASETS_VIEW);
    }

    @RequestMapping(value = "/project/{dataset}/form/order.html", method = RequestMethod.GET)
    public ModelAndView getProjectOrderForm(@PathVariable("dataset") Dataset dataset) {

        this.authorizationUtils.authorizeForWrite(dataset);

        return new ModelAndView(PROJECT_ORDER_DATASETS_VIEW);
    }

    @RequestMapping(value = {"/dataset/{dataset}/orderDatasets.html"}, method = RequestMethod.POST)
    public ModelAndView orderDatasetsForDataset(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") DatasetOrderCommand command,
                                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(DATASET_ORDER_DATASETS_VIEW);

        } else {

            List<Dataset> movingDatasets = command.getMovingDatasets();

            this.cadisDatasetService.orderChildDatasets(dataset, movingDatasets);

            redirectAttributes.addFlashAttribute("successMessage", "Datasets re-ordered.");

            modelAndView = new ModelAndView("redirect:/dataset/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }

    @RequestMapping(value = {"/project/{dataset}/orderDatasets.html"}, method = RequestMethod.POST)
    public ModelAndView orderDatasetsForProject(@PathVariable(value = "dataset") Dataset dataset, @ModelAttribute("command") DatasetOrderCommand command,
                                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        this.authorizationUtils.authorizeForWrite(dataset);

        ModelAndView modelAndView;

        if (bindingResult.hasErrors()) {

            modelAndView = new ModelAndView(PROJECT_ORDER_DATASETS_VIEW);

        } else {

            List<Dataset> movingDatasets = command.getMovingDatasets();

            this.cadisDatasetService.orderChildDatasets(dataset, movingDatasets);

            redirectAttributes.addFlashAttribute("successMessage", "Datasets re-ordered.");

            modelAndView = new ModelAndView("redirect:/project/" + dataset.getShortName() + ".html");
        }

        return modelAndView;
    }
}
