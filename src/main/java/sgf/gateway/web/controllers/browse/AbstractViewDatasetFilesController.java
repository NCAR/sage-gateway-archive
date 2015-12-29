package sgf.gateway.web.controllers.browse;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.security.AuthorizationUtils;
import sgf.gateway.web.controllers.browse.models.DatasetDownloadRow;
import sgf.gateway.web.controllers.browse.models.LogicalFileDownloadRow;

import java.util.*;

public abstract class AbstractViewDatasetFilesController {

    protected final DatasetRepository datasetRepository;

    protected final String viewName;

    protected final AuthorizationUtils authorizationUtils;

    public AbstractViewDatasetFilesController(DatasetRepository datasetRepository, AuthorizationUtils authorizationUtils, String viewName) {

        this.datasetRepository = datasetRepository;
        this.authorizationUtils = authorizationUtils;
        this.viewName = viewName;
    }

    DatasetRepository getDatasetRepository() {
        return datasetRepository;
    }

    String getViewName() {
        return viewName;
    }

    AuthorizationUtils getAuthorizationUtils() {
        return authorizationUtils;
    }

    void authorizeCollections(Collection<Dataset> datasetList) {

        for (Dataset dataset : datasetList) {

            authorizationUtils.authorizeForRead(dataset);
        }
    }

    Collection<DatasetDownloadRow> createDatasetDownloadRowCollection(Collection<Dataset> datasetCollection) {

        Collection<DatasetDownloadRow> datasetDownloadRowCollection = new ArrayList<>();

        for (Dataset dataset : datasetCollection) {

            Collection<LogicalFileDownloadRow> logicalFileDownloadRows = createLogicalFileDownloadRowCollection(dataset.getCurrentDatasetVersion().getLogicalFiles());

            DatasetDownloadRow datasetDownloadRow = new DatasetDownloadRow(dataset, logicalFileDownloadRows);

            datasetDownloadRowCollection.add(datasetDownloadRow);
        }

        return datasetDownloadRowCollection;
    }

    Collection<LogicalFileDownloadRow> createLogicalFileDownloadRowCollection(Collection<LogicalFile> logicalFileCollection) {

        List<LogicalFileDownloadRow> logicalFileDownloadRowCollection = new ArrayList<>();

        for (LogicalFile logicalFile : logicalFileCollection) {

            LogicalFileDownloadRow logicalFileDownloadRow = new LogicalFileDownloadRow(logicalFile);

            logicalFileDownloadRowCollection.add(logicalFileDownloadRow);
        }

        // Order the rows lexicographically

        Collections.sort(logicalFileDownloadRowCollection, new Comparator<LogicalFileDownloadRow>() {

            public int compare(LogicalFileDownloadRow logicalFileDownloadRow1, LogicalFileDownloadRow logicalFileDownloadRow2) {

                int result = logicalFileDownloadRow1.getName().compareTo(logicalFileDownloadRow2.getName());

                return result;
            }
        });

        return logicalFileDownloadRowCollection;
    }
}
