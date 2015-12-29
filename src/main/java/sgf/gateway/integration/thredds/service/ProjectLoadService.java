package sgf.gateway.integration.thredds.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sgf.gateway.integration.thredds.ThreddsDatasetChildrenPayload;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.CadisProjectService;

public class ProjectLoadService {

    private final static Logger LOG = LoggerFactory.getLogger(ProjectLoadService.class);

    private final CadisProjectService projectService;

    public ProjectLoadService(CadisProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    public ThreddsDatasetChildrenPayload load(ThreddsDatasetPayload payload) {

        if (LOG.isInfoEnabled()) {
            LOG.info("loading project payload: " + payload);
        }

        Dataset dataset = projectService.saveOrUpdate(payload);

        ThreddsDatasetChildrenPayload childrenPayload = new ThreddsDatasetChildrenPayload();

        childrenPayload.setParentDatasetShortName(dataset.getShortName());
        childrenPayload.setThreddsDatasetURIs(payload.getThreddsDatasetURIs());
        childrenPayload.setDataCenterName(payload.getDataCenterName());

        return childrenPayload;
    }
}
