package sgf.gateway.validation.persistence;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.web.controllers.cadis.publish.command.CadisDatasetCommand;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssertUniqueDatasetShortNameValidatorTest {

    private static final String DATASET_SHORT_NAME = "dataset_name";
    private static final UUID COMMAND_IDENTIFIER = UUID.valueOf("e555ba20-6c8f-11e2-bcfd-0800200c9a66");
    private static final UUID DATASET_SAME_IDENTIFIER = UUID.valueOf("e555ba20-6c8f-11e2-bcfd-0800200c9a66");
    private static final UUID DATASET_DIFFERENT_IDENTIFIER = UUID.valueOf("4d0192f0-6c92-11e2-bcfd-0800200c9a66");
    private Dataset mockDataset;
    private DatasetRepository mockDatasetRepository;
    private CadisDatasetCommand stubCadisDatasetCommand;

    private AssertUniqueShortNameValidator validator;

    @Before
    public void before() {

        mockDatasetRepository = mock(DatasetRepository.class);

        mockDataset = mock(Dataset.class);
        when(mockDataset.getShortName()).thenReturn(DATASET_SHORT_NAME);

        stubCadisDatasetCommand = new CadisDatasetCommand();
        stubCadisDatasetCommand.setShortName(DATASET_SHORT_NAME);
        stubCadisDatasetCommand.setIdentifier(COMMAND_IDENTIFIER);

        AssertUniqueShortName assertUniqueDatasetShortName = mock(AssertUniqueShortName.class);
        when(assertUniqueDatasetShortName.identifierField()).thenReturn("identifier");
        when(assertUniqueDatasetShortName.shortNameField()).thenReturn("shortName");

        validator = new AssertUniqueShortNameValidator(mockDatasetRepository);
        validator.initialize(assertUniqueDatasetShortName);
    }

    @Test
    public void testNoDatasetFound() {

        boolean valid = validator.isValid(stubCadisDatasetCommand, null);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testSameDatasetFound() {

        when(mockDataset.getIdentifier()).thenReturn(DATASET_SAME_IDENTIFIER);

        when(mockDatasetRepository.getByShortName(DATASET_SHORT_NAME)).thenReturn(mockDataset);


        boolean valid = validator.isValid(stubCadisDatasetCommand, null);

        assertThat(valid, equalTo(true));
    }

    @Test
    public void testDifferentDatasetFound() {

        when(mockDataset.getIdentifier()).thenReturn(DATASET_DIFFERENT_IDENTIFIER);

        when(mockDatasetRepository.getByShortName(DATASET_SHORT_NAME)).thenReturn(mockDataset);


        boolean valid = validator.isValid(stubCadisDatasetCommand, null);

        assertThat(valid, equalTo(true));
    }
}
