package sgf.gateway.validation.persistence;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShortNameAvailableValidatorTest {

    private ShortNameAvailableValidator validator;
    private DatasetRepository fakeDatasetRepository;

    @Before
    public void setup() {

        this.fakeDatasetRepository = mock(DatasetRepository.class);

        this.validator = new ShortNameAvailableValidator(this.fakeDatasetRepository);
    }

    @Test
    public void nullShortNamePassesValidation() {

        assertValid(null);
    }

    @Test
    public void blankShortNamesPassValidation() {

        assertValid("");
        assertValid(" ");
        assertValid("                    ");
    }

    private void assertValid(String shortName) {

        boolean valid = this.validator.isValid(shortName, null);

        assertThat(valid, is(true));
    }

    @Test
    public void shortNameNotInDatabasePassesValidation() {

        when(this.fakeDatasetRepository.getByShortNameIgnoreCase("test")).thenReturn(null);

        assertValid("test");
    }

    @Test
    public void shortNameInDatabaseFailsValidation() {

        when(this.fakeDatasetRepository.getByShortNameIgnoreCase("test")).thenReturn(mock(Dataset.class));

        assertInvalid("test");
    }

    private void assertInvalid(String shortName) {

        boolean valid = this.validator.isValid(shortName, null);

        assertThat(valid, is(false));
    }
}
