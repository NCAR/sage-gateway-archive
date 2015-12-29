package sgf.gateway.model.metadata.descriptive.builder.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.descriptive.Institution;
import sgf.gateway.model.metadata.descriptive.builder.InstitutionBuilder;
import sgf.gateway.model.metadata.descriptive.factory.InstitutionFactory;

import java.util.HashMap;
import java.util.Map;

public class InstitutionBuilderImpl implements InstitutionBuilder {

    private static final Log LOG = LogFactory.getLog(InstitutionBuilderImpl.class);

    private DatasetRepository datasetRepository;
    private InstitutionFactory institutionFactory;
    private boolean allowCreate = true;

    private Map<String, Institution> cache;

    public InstitutionBuilderImpl(DatasetRepository datasetRepository, InstitutionFactory institutionFactory, boolean allowCreate) {

        this.datasetRepository = datasetRepository;
        this.institutionFactory = institutionFactory;
        this.allowCreate = allowCreate;

        this.cache = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    public Institution build(String institutionName) {

        String trimmedInstitutionName = institutionName.trim();

        if (LOG.isTraceEnabled()) {
            LOG.trace("Building Institution: " + trimmedInstitutionName);
        }

        Institution institution = find(trimmedInstitutionName);

        if (allowCreate && (null == institution)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Couldn't find institution, creating a new instance");
            }
            institution = create(trimmedInstitutionName);
        }
        if (LOG.isTraceEnabled()) {
            LOG.trace("Building return Institution: " + institution);
        }

        return institution;
    }

    protected Institution find(String institution) {

        Institution result;

        if (this.cache.containsKey(institution)) {
            result = this.cache.get(institution);
        } else {
            result = this.datasetRepository.findInstitution(institution);
            if (null != result) {
                this.cache.put(institution, result);
            }
        }
        return result;
    }

    protected Institution create(String institution) {

        Institution result = this.institutionFactory.create(institution);

        this.cache.put(institution, result);
        return result;
    }
}
