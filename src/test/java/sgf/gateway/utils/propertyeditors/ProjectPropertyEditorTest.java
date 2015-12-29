package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.service.metadata.ProjectNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProjectPropertyEditorTest {

    private Project mockProject;
    private ProjectRepository mockProjectRepository;
    private ProjectPropertyEditor ProjectPropertyEditor;

    @Before
    public void setup() {

        this.mockProject = mock(Project.class);
        this.mockProjectRepository = mock(ProjectRepository.class);
        this.ProjectPropertyEditor = new ProjectPropertyEditor(mockProjectRepository);
    }

    @Test
    public void testGetValue() {

        final String projectIdentifier = "project_id";
        when(mockProjectRepository.findProjectByPersistentIdentifier(projectIdentifier)).thenReturn(mockProject);

        ProjectPropertyEditor.setAsText(projectIdentifier);

        Project Project = (Project) ProjectPropertyEditor.getValue();
        assertThat(Project, equalTo(mockProject));
    }


    @Test
    public void testGetAsText() {

        String projectIdentifier = "project_id";
        when(mockProjectRepository.findProjectByPersistentIdentifier(projectIdentifier)).thenReturn(mockProject);
        when(mockProject.getPersistentIdentifier()).thenReturn(projectIdentifier);

        ProjectPropertyEditor.setAsText(projectIdentifier);
        String ProjectPersistentId = ProjectPropertyEditor.getAsText();

        assertThat(ProjectPersistentId, is(projectIdentifier));
    }


    @Test(expected = ProjectNotFoundException.class)
    public void testIdentiferNotFound() {

        when(mockProjectRepository.findProjectByPersistentIdentifier("Project_identifer_not_found")).thenReturn(null);

        ProjectPropertyEditor.setAsText("Project_identifer_not_found");
    }

    @Test
    public void testNoText() {

        ProjectPropertyEditor.setAsText("");

        Project Project = (Project) ProjectPropertyEditor.getValue();

        assertNullProject(Project);
    }

    private void assertNullProject(Project Project) {

        assertThat(Project, equalTo(null));
    }

    @Test
    public void testNullText() {

        ProjectPropertyEditor.setAsText(null);

        Project Project = (Project) ProjectPropertyEditor.getValue();

        assertNullProject(Project);
    }
}
