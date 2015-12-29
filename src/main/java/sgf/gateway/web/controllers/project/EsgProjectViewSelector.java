package sgf.gateway.web.controllers.project;

import org.safehaus.uuid.UUID;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.service.metadata.ProjectNotFoundException;
import sgf.gateway.web.controllers.browse.ProjectViewHandler;

import java.util.List;

public class EsgProjectViewSelector implements ProjectViewSelector {

    private final List<ProjectViewHandler> projectViewHandlers;
    private final ProjectRepository projectRepository;

    public EsgProjectViewSelector(List<ProjectViewHandler> projectViewHandlers, ProjectRepository projectRepository) {
        this.projectViewHandlers = projectViewHandlers;
        this.projectRepository = projectRepository;
    }

    @Override
    public ModelAndView selectProjectView(String shortName) {

        Project project = this.getProject(shortName);

        ProjectViewHandler projectViewHandler = this.selectProjectViewHandler(project);

        ModelAndView modelAndView = projectViewHandler.handleProjectView(project);

        return modelAndView;
    }

    @Override
    public ModelAndView selectProjectListingView() {

        ModelMap model = new ModelMap("projects", this.projectRepository.getAll());

        return new ModelAndView("project/projectlisting", model);
    }

    @Override
    public ModelAndView selectProjectByIdView(UUID projectId) {

        Project project = this.getProject(projectId);

        ModelAndView modelAndView = new ModelAndView("redirect:/project/" + project.getPersistentIdentifier() + ".html");

        return modelAndView;
    }

    private Project getProject(UUID projectId) {

        Project project = this.projectRepository.get(projectId);

        if (project == null) {
            throw new ProjectNotFoundException(projectId.toString());
        }

        return project;
    }

    private Project getProject(String shortName) {

        Project project = this.projectRepository.findProjectByPersistentIdentifier(shortName);

        if (project == null) {
            throw new ProjectNotFoundException(shortName);
        }

        return project;
    }

    private ProjectViewHandler selectProjectViewHandler(Project project) {

        for (ProjectViewHandler handler : this.projectViewHandlers) {
            if (handler.supports(project)) {
                return handler;
            }
        }

        return null;
    }
}