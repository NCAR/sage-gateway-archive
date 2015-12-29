package sgf.gateway.web.controllers.browse;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.utils.spring.SelectableElement;
import sgf.gateway.web.controllers.browse.models.DatasetDownloadRow;

import java.util.*;

@Controller
public class ViewDatasetFilesController extends AbstractViewDatasetFilesController {

    public ViewDatasetFilesController(final DatasetRepository datasetRepository, final AuthorizationUtils authorizationUtils, final String viewName) {

        super(datasetRepository, authorizationUtils, viewName);
    }

    @RequestMapping(value = "/browse/viewCollectionFilesInitial.html", method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam("datasetId") UUID[] identifiers) throws Exception {

        Collection<Dataset> datasetCollection = datasetRepository.get(Arrays.asList(identifiers));

        authorizeCollections(datasetCollection);

        ModelAndView modelAndView = new ModelAndView(viewName);

        Collection<DatasetDownloadRow> datasetDownloadRows = createDatasetDownloadRowCollection(datasetCollection);

        modelAndView.addObject("datasetDownloadRows", datasetDownloadRows);
        modelAndView.addObject("variableSet", createVariableSelectableElements(datasetCollection));
        modelAndView.addObject("filenamePattern", "");

        return modelAndView;
    }

    List<SelectableElement<Variable>> createVariableSelectableElements( Collection<Dataset> datasetList) {

        Set<Variable> variableSet = new HashSet<>();

        for (Dataset dataset : datasetList) {

            if (dataset.isGeophysicalDataset()) {

                variableSet.addAll(dataset.getCurrentDatasetVersion().getVariables());
            }
        }

        List<Variable> variableList = new ArrayList<>(variableSet);

        Collections.sort(variableList, new Comparator<Variable>() {

            public int compare(final Variable variable1, final Variable variable2) {

                int result = variable1.getName().compareTo(variable2.getName());

                return result;
            }
        });

        List<SelectableElement<Variable>> selectableElementList = createVariableSelectableElements2(variableList);

        return selectableElementList;
    }

    List<SelectableElement<Variable>> createVariableSelectableElements2(List<Variable> variableList) {

        List<SelectableElement<Variable>> selectableElementList = new ArrayList<>();

        for (Variable variable : variableList) {

            SelectableElement<Variable> selectableElement = new SelectableElement<>(false, variable);
            selectableElementList.add(selectableElement);
        }

        return selectableElementList;
    }
}
