package sgf.gateway.service.metadata;

import sgf.gateway.model.metadata.activities.observing.PlatformType;
import sgf.gateway.web.controllers.observing.PlatformTypeRequest;


public interface PlatformTypeService {

    PlatformType addPlatformType(PlatformTypeRequest command);
}
