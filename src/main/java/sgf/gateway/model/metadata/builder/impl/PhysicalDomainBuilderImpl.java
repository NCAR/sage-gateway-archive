package sgf.gateway.model.metadata.builder.impl;

import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.PhysicalDomain;
import sgf.gateway.model.metadata.builder.PhysicalDomainBuilder;
import sgf.gateway.model.metadata.factory.PhysicalDomainFactory;

public class PhysicalDomainBuilderImpl implements PhysicalDomainBuilder {

    private DatasetRepository datasetRepository;
    private PhysicalDomainFactory physicalDomainFactory;
    private boolean allowCreate = true;

    public PhysicalDomainBuilderImpl(DatasetRepository datasetRepository, PhysicalDomainFactory physicalDomainFactory, boolean allowCreate) {

        this.datasetRepository = datasetRepository;
        this.physicalDomainFactory = physicalDomainFactory;
        this.allowCreate = allowCreate;
    }

    /*
     * (non-Javadoc)
     *
     * @see sgf.gateway.model.metadata.builder.PhysicalDomainBuilder#build(java.lang.String)
     */
    public synchronized PhysicalDomain build(String physicalDomainName) {

        String trimmedPhysicalDomainName = physicalDomainName.trim();

        PhysicalDomain physicalDomain = find(trimmedPhysicalDomainName);

        if (allowCreate && (null == physicalDomain)) {

            physicalDomain = create(trimmedPhysicalDomainName);
        }

        return physicalDomain;
    }

    protected PhysicalDomain find(String physicalDomain) {

        PhysicalDomain result;

        result = this.datasetRepository.findPhysicalDomain(physicalDomain);

        return result;
    }

    protected PhysicalDomain create(String physicalDomain) {

        PhysicalDomain result = this.physicalDomainFactory.create(physicalDomain);

        return result;
    }

}
