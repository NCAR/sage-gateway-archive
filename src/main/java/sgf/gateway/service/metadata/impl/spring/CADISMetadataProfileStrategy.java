package sgf.gateway.service.metadata.impl.spring;

import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.MetadataProfile;
import sgf.gateway.service.metadata.MetadataProfileStrategy;
import sgf.gateway.utils.spring.Constants;

public class CADISMetadataProfileStrategy implements MetadataProfileStrategy {

    private final MetadataRepository metadataRepository;

    public CADISMetadataProfileStrategy(MetadataRepository metadataRepository) {

        this.metadataRepository = metadataRepository;
    }

    @Override
    public MetadataProfile getMetadataProfileReference() {

        MetadataProfile metadataProfile = this.metadataRepository.findMetadataProfileByName(Constants.CADIS_PROFILE_NAME);

        return metadataProfile;
    }
}
