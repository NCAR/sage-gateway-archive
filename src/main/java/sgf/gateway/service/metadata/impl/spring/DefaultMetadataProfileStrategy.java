package sgf.gateway.service.metadata.impl.spring;

import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.MetadataProfile;
import sgf.gateway.service.metadata.MetadataProfileStrategy;
import sgf.gateway.utils.spring.Constants;

public class DefaultMetadataProfileStrategy implements MetadataProfileStrategy {

    private final MetadataRepository metadataRepository;

    public DefaultMetadataProfileStrategy(MetadataRepository metadataRepository) {

        this.metadataRepository = metadataRepository;
    }

    @Override
    public MetadataProfile getMetadataProfileReference() {

        MetadataProfile metadataProfile = this.metadataRepository.findMetadataProfileByName(Constants.DEFAULT_PROFILE_NAME);

        return metadataProfile;
    }

}
