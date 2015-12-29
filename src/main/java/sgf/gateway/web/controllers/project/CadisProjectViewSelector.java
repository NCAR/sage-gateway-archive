package sgf.gateway.web.controllers.project;

import org.safehaus.uuid.UUID;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.metadata.ProjectNotFoundException;
import sgf.gateway.web.controllers.browse.cadis.CADISProjectViewHandler;

public class CadisProjectViewSelector implements ProjectViewSelector {

    private final CADISProjectViewHandler viewHandler;
    private final DatasetRepository datasetRepository;

    public CadisProjectViewSelector(CADISProjectViewHandler viewHandler, DatasetRepository datasetRepository) {
        this.viewHandler = viewHandler;
        this.datasetRepository = datasetRepository;
    }

    @Override
    public ModelAndView selectProjectView(String shortName) {

        Dataset project = this.getProject(shortName);

        return this.viewHandler.handleProjectView(project);
    }

    @Override
    public ModelAndView selectProjectListingView() {

        ModelMap model = new ModelMap("projects", this.datasetRepository.getProjectTypeDatasets());

        return new ModelAndView("cadis/project/projectlisting", model);
    }

    @Override
    public ModelAndView selectProjectByIdView(UUID projectId) {

        Dataset project = this.getProject(projectId);

        ModelAndView modelAndView = new ModelAndView("redirect:/project/" + project.getShortName() + ".html");

        return modelAndView;
    }

    private Dataset getProject(UUID projectId) {

        Dataset project = this.datasetRepository.get(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }

        return project;
    }

    private Dataset getProject(String shortName) {

        Dataset project = this.datasetRepository.getProjectByShortName(shortName);

        if (project == null) {
            throw new ProjectNotFoundException(shortName);
        }

        return project;
    }

}
