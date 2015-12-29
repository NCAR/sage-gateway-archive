package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.io.Serializable;
import java.util.List;

public class ProjectRepositoryImpl extends AbstractRepositoryImpl<Project, Serializable> implements ProjectRepository {

    @Override
    protected Class<Project> getEntityClass() {
        return Project.class;
    }

    /**
     * Get project by id.  If not found, Exception.
     */
    @Override
    public Project get(UUID projectIdentity) {

        Project result = super.get(projectIdentity);

        if (null == result) {
            throw new ObjectNotFoundException(projectIdentity);
        }

        return result;
    }


    @Override
    public List<Project> getAll() {
        return super.getAllOrdered("name");
    }

    /**
     * Find project by persistent identifier, case-insensitive
     */
    @Override
    public Project findProjectByPersistentIdentifier(String persistentId) {

        return super.findUniqueByCriteria(Restrictions.eq("persistentIdentifier", persistentId).ignoreCase());
    }

    /**
     * Returns unique project with given name.
     */
    @Override
    public Project findProjectByName(String name) {

        return super.findUniqueByCriteria(Restrictions.eq("name", name).ignoreCase());
    }

    /**
     * Returns a single project with a similar name.
     */
    @Override
    public Project findLikeProject(String name) {

        return super.findUniqueByCriteria(Restrictions.ilike("name", name + "%"));
    }

}