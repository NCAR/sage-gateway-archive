package sgf.gateway.model.metadata.activities.project.factory.impl;

import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.model.metadata.activities.project.ProjectImpl;
import sgf.gateway.model.metadata.activities.project.factory.ProjectFactory;
import sgf.gateway.utils.FileNameAndURIRenameStrategy;

public class ProjectFactoryImpl implements ProjectFactory {

    private final NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    private final FileNameAndURIRenameStrategy identifierRenameStrategy;

    public ProjectFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy, FileNameAndURIRenameStrategy identifierRenameStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
        this.identifierRenameStrategy = identifierRenameStrategy;
    }

    public Project createProject(String name) {

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(ProjectImpl.class);

        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        ProjectImpl project = new ProjectImpl(vuId.getIdentifierValue(), vuId.getVersion(), name);

        String persistentIdentifier = identifierRenameStrategy.rename(project.getName());

        project.setPersistentIdentifier(persistentIdentifier);

        return project;
    }
}
