package sgf.gateway.validation.persistence;

import org.junit.Test;
import sgf.gateway.dao.metadata.ProjectRepository;
import sgf.gateway.model.metadata.activities.project.Project;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ActivityProjectNameUniqueValidatorTest {

    @Test
    public void testEmptyProjectName() {

        ActivityProjectNameUniqueValidator validator = createValidator(null);

        assertThat(validator.isValid(null, null), is(false));
        assertThat(validator.isValid("", null), is(false));
        assertThat(validator.isValid(" ", null), is(false));
    }

    public ActivityProjectNameUniqueValidator createValidator(ProjectRepository projectRepository) {

        return new ActivityProjectNameUniqueValidator(projectRepository);
    }

    @Test
    public void testProjectIsFound() {

        Project mockProject = mock(Project.class);

        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.findProjectByName("CCSM")).thenReturn(mockProject);

        ActivityProjectNameUniqueValidator validator = createValidator(mockProjectRepository);

        assertThat(validator.isValid("CCSM", null), is(false));
    }

    @Test
    public void testProjectIsNotFound() {

        ProjectRepository mockProjectRepository = mock(ProjectRepository.class);
        when(mockProjectRepository.findProjectByName("CCSM")).thenReturn(null);

        ActivityProjectNameUniqueValidator validator = createValidator(mockProjectRepository);

        assertThat(validator.isValid("CCSM", null), is(true));
    }
}
