package sgf.gateway.web.controllers.browse;

import org.safehaus.uuid.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.DataFormat;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.Variable;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.utils.spring.SelectableElement;
import sgf.gateway.web.controllers.browse.models.DatasetDownloadRow;
import sgf.gateway.web.controllers.browse.models.LogicalFileDownloadRow;

import java.util.*;

@Controller
public class ViewDatasetFilesFilterController extends AbstractViewDatasetFilesController {

    public ViewDatasetFilesFilterController(DatasetRepository datasetRepository, AuthorizationUtils authorizationUtils, String viewName) {

        super(datasetRepository, authorizationUtils, viewName);
    }

    @RequestMapping(value = "/browse/viewCollectionFilesFilter.html", method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam("datasetId") UUID[] datasetIdentifiers, @RequestParam(value = "filenamePattern", required = false) String filenamePattern, @RequestParam(value = "variableId", required = false) UUID[] variableIdentifiers) throws Exception {

        // TODO: Is this the right place to be doing input munging?
        if (filenamePattern == null) {
            filenamePattern = "";
        }
        String mungedFilenamePattern = filenamePattern.replace('*', '%');

        Collection<Dataset> datasetCollection = this.datasetRepository.get(Arrays.asList(datasetIdentifiers));

        if (variableIdentifiers == null) {

            variableIdentifiers = new UUID[0];
        }

        ModelAndView modelAndView = generateView(datasetCollection, Arrays.asList(variableIdentifiers), filenamePattern, mungedFilenamePattern);

        return modelAndView;
    }

    protected ModelAndView generateView(Collection<Dataset> datasetCollection,
                                        List<UUID> variableIdentityList, String filenamePattern, String mungedFilenamePattern) {

        authorizeCollections(datasetCollection);

        ModelAndView modelAndView = new ModelAndView(this.viewName);

        Collection<DatasetDownloadRow> datasetDownloadRows = this.createDatasetDownloadRowCollection(datasetCollection, mungedFilenamePattern, variableIdentityList);


        modelAndView.addObject("datasetDownloadRows", datasetDownloadRows);
        modelAndView.addObject("filenamePattern", filenamePattern);
        modelAndView.addObject("variableSet", createVariableSelectableElements(datasetCollection, variableIdentityList));

        return modelAndView;
    }

    Collection<DatasetDownloadRow> createDatasetDownloadRowCollection(Collection<Dataset> datasetCollection, String mungedFilenamePattern,
                                                                      Collection<UUID> variableIdentities) {

        Collection<DatasetDownloadRow> datasetDownloadRowCollection = new ArrayList<>();

        for (Dataset dataset : datasetCollection) {

            Collection<LogicalFile> logicalFiles = this.datasetRepository.getFilteredLogicalFiles(dataset, mungedFilenamePattern, variableIdentities);

            Collection<LogicalFileDownloadRow> logicalFileDownloadRows = createLogicalFileDownloadRowCollection(logicalFiles);

            DatasetDownloadRow datasetDownloadRow = new DatasetDownloadRow(dataset, logicalFileDownloadRows);

            datasetDownloadRowCollection.add(datasetDownloadRow);
        }

        return datasetDownloadRowCollection;
    }

    List<SelectableElement<DataFormat>> createDataFormatSelectableElements(List<DataFormat> dataFormatList,
                                                                           List<UUID> dataFormatIdentityList) {

        List<SelectableElement<DataFormat>> selectableElementList = new ArrayList<>();

        for (DataFormat dataFormat : dataFormatList) {

            boolean selected = false;

            if (dataFormatIdentityList.contains(dataFormat.getIdentifier())) {
                selected = true;
            }

            SelectableElement<DataFormat> selectableElement = new SelectableElement<>(selected, dataFormat);
            selectableElementList.add(selectableElement);
        }
        return selectableElementList;
    }

    List<SelectableElement<Variable>> createVariableSelectableElements(Collection<Dataset> datasetList,
                                                                       Collection<UUID> variableIdentities) {

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

        List<SelectableElement<Variable>> selectableElementList = createVariableSelectableElements2(variableList, variableIdentities);

        return selectableElementList;
    }

    List<SelectableElement<Variable>> createVariableSelectableElements2(List<Variable> variableList,
                                                                        Collection<UUID> variableIdentities) {

        List<SelectableElement<Variable>> selectableElementList = new ArrayList<>();

        List<UUID> variableUUIDList = new ArrayList<>();

        for (UUID objectIdentity : variableIdentities) {
            variableUUIDList.add(objectIdentity);
        }

        for (Variable variable : variableList) {

            boolean selected = false;

            if (variableUUIDList.contains(variable.getIdentifier())) {

                selected = true;
            }

            SelectableElement<Variable> selectableElement = new SelectableElement<>(selected, variable);
            selectableElementList.add(selectableElement);
        }

        return selectableElementList;
    }
}
