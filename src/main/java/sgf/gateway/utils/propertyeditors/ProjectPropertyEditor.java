package sgf.gateway.utils.propertyeditors;

import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.service.metadata.ProjectNotFoundException;

import java.beans.PropertyEditorSupport;

public class ProjectPropertyEditor extends PropertyEditorSupport {

    private final ProjectRepository projectRepository;

    public ProjectPropertyEditor(ProjectRepository projectRepository) {

        this.projectRepository = projectRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            Project project = this.projectRepository.findProjectByPersistentIdentifier(text);

            if (null == project) {
                throw new ProjectNotFoundException(text);
            }

            setValue(project);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        Project value = (Project) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getPersistentIdentifier();
        }

        return result;
    }
}
