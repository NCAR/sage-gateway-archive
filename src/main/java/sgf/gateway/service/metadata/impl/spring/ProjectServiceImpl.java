package sgf.gateway.service.metadata.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.metadata.activities.project.factory.ProjectFactory;
import sgf.gateway.service.metadata.ProjectService;
import sgf.gateway.web.controllers.project.command.ProjectCommandESG;

public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectFactory projectFactory;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectFactory projectFactory) {
        this.projectRepository = projectRepository;
        this.projectFactory = projectFactory;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Project save(ProjectCommandESG command) {

        Project newProject = projectFactory.createProject(command.getTitle());

        newProject.setDescription(command.getDescription());

        projectRepository.add(newProject);

        return newProject;
    }

}
