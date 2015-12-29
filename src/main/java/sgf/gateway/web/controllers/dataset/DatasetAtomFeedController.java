package sgf.gateway.web.controllers.dataset;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;

import java.util.*;

@Controller
public class DatasetAtomFeedController {

    private DatasetRepository datasetRepository;

    public DatasetAtomFeedController(DatasetRepository datasetRepository) {

        this.datasetRepository = datasetRepository;
    }

    @RequestMapping(value = "/dataset.atom", method = RequestMethod.GET)
    public ModelAndView getPaginatedDatasetAtomFeed(@RequestParam(value = "offset", required = false) Integer offset, @RequestParam(value = "rpp", required = false) Integer resultsPerPage) {

        ModelAndView modelAndView = null;

        if (offset == null || resultsPerPage == null) {

            modelAndView = this.getRedirectModelAndView(offset, resultsPerPage);

        } else {

            modelAndView = this.getAtomFeedModelAndView(offset, resultsPerPage);
        }

        return modelAndView;
    }

    private ModelAndView getRedirectModelAndView(Integer offset, Integer resultsPerPage) {

        RedirectView redirectView = new RedirectView("/dataset.atom", true);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        Map<String, Integer> attributesMap = new HashMap<String, Integer>();

        if (offset == null) {
            attributesMap.put("offset", 0);
        } else {
            attributesMap.put("offset", offset);
        }

        if (resultsPerPage == null) {
            attributesMap.put("rpp", 30);
        } else {
            attributesMap.put("rpp", resultsPerPage);
        }

        redirectView.setAttributesMap(attributesMap);

        return new ModelAndView(redirectView);
    }

    private ModelAndView getAtomFeedModelAndView(Integer offset, Integer resultsPerPage) {

        List<Dataset> datasets = this.datasetRepository.getPaginatedByDateUpdatedDescending(offset, resultsPerPage);

        ModelAndView modelAndView = new ModelAndView("browse/view-dataset-feed");
        modelAndView.addObject("datasets", datasets);

        modelAndView.addObject("offset", offset);
        modelAndView.addObject("resultsPerPage", resultsPerPage);

        Long totalDatasetCount = this.datasetRepository.getTotalCount();

        modelAndView.addObject("totalDatasetCount", totalDatasetCount);

        return modelAndView;
    }

    @RequestMapping(value = "datasets.atom", method = RequestMethod.GET)
    public ModelAndView redirectDatasetFeed() throws Exception {

        RedirectView redirectView = new RedirectView("/dataset.atom", true);
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

        return new ModelAndView(redirectView);
    }

    @RequestMapping(value = "/dataset/{dataset}/version.atom", method = RequestMethod.GET)
    public ModelAndView datasetVersionFeed(@PathVariable(value = "dataset") Dataset dataset) throws Exception {

        Collection<DatasetVersion> datasetVersions = dataset.getDatasetVersions();

        List<DatasetVersion> datasetVersionList = new ArrayList<DatasetVersion>(datasetVersions);

        Collections.sort(datasetVersionList, new DatasetVersionDateUpdatedComparator());

        ModelAndView modelAndView = new ModelAndView("browse/view-dataset-version-feed");
        modelAndView.addObject("dataset", dataset);
        modelAndView.addObject("datasetVersions", datasetVersionList);

        return modelAndView;
    }
}
