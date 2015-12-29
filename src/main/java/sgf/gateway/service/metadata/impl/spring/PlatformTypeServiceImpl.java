package sgf.gateway.service.metadata.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.model.metadata.activities.observing.factory.PlatformTypeFactory;
import sgf.gateway.service.metadata.PlatformTypeService;
import sgf.gateway.web.controllers.observing.PlatformTypeRequest;


public class PlatformTypeServiceImpl implements PlatformTypeService {

    private final PlatformTypeRepository platformTypeRepository;
    private final PlatformTypeFactory platformTypeFactory;


    public PlatformTypeServiceImpl(PlatformTypeRepository platformTypeRepository, PlatformTypeFactory platformTypeFactory) {

        super();

        this.platformTypeRepository = platformTypeRepository;
        this.platformTypeFactory = platformTypeFactory;
    }


    /**
     * Add new PlatformType based on command object from form.
     *
     * @param command
     * @return PlatformType
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PlatformType addPlatformType(final PlatformTypeRequest command) {

        PlatformType platformType = platformTypeFactory.create(command.getShortName());
        platformTypeRepository.add(platformType);

        return platformType;
    }

}
