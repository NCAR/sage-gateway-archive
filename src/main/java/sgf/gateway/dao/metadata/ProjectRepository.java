package sgf.gateway.dao.metadata;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.activities.project.Project;

import java.io.Serializable;

/**
 * Interface for specific Project activities.
 * <p/>
 * Please see the following link which helps explaining the Repository Pattern:
 * http://devlicio.us/blogs/casey/archive/2009/02/20/ddd-the-repository-pattern.aspx
 *
 * @author
 */
public interface ProjectRepository extends Repository<Project, Serializable> {

    Project get(UUID projectIdentity);

    Project findProjectByPersistentIdentifier(String persistentId);

    Project findProjectByName(String name);

    Project findLikeProject(String name);

}
