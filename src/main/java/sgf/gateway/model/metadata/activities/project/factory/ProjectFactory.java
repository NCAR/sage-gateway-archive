package sgf.gateway.model.metadata.activities.project.factory;

import sgf.gateway.model.metadata.activities.project.Project;

public interface ProjectFactory {

    Project createProject(String name);
}
