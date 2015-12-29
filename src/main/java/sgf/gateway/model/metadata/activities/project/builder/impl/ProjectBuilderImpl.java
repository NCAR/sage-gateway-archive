package sgf.gateway.model.metadata.activities.project.builder.impl;

import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.metadata.activities.project.builder.ProjectBuilder;
import sgf.gateway.model.metadata.activities.project.factory.ProjectFactory;

public class ProjectBuilderImpl implements ProjectBuilder {

    private ProjectRepository projectRepository;
    private ProjectFactory projectFactory;

    public ProjectBuilderImpl(ProjectRepository projectRepository, ProjectFactory projectFactory) {

        this.projectRepository = projectRepository;
        this.projectFactory = projectFactory;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized Project build(String name) {

        String projectName = name.trim();

        Project project = find(projectName);

        if (null == project) {

            project = create(projectName);
        }

        return project;
    }

    protected Project find(String projectName) {

        Project result;

        result = this.projectRepository.findProjectByName(projectName);
        if (result == null) {
            result = this.projectRepository.findLikeProject(projectName);
        }

        return result;
    }

    protected Project create(String projectName) {
        Project result = this.projectFactory.createProject(projectName);
        result.setDescription(projectName);

        return result;
    }

}
