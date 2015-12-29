package sgf.gateway.search.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import java.util.Collection;
import java.util.Iterator;

public class ReindexDatasetSource implements ReindexObjectSource {

    private static final Log LOG = LogFactory.getLog(ReindexObjectSource.class);

    private final DatasetRepository datasetRepository;

    public ReindexDatasetSource(DatasetRepository datasetRepository) {
        super();
        this.datasetRepository = datasetRepository;
    }

    @Override
    public Iterator<Object> getIterator() {

        Collection<Dataset> datasets = this.datasetRepository.getAll();
        Iterator iterator = datasets.iterator();

        LOG.debug("Reindexing " + datasets.size() + " datasets");

        return iterator;
    }
}
