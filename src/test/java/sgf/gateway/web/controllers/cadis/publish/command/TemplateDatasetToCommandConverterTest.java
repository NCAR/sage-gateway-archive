package sgf.gateway.web.controllers.cadis.publish.command;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.Dataset;

import static org.mockito.Mockito.*;

public class TemplateDatasetToCommandConverterTest {

    private Dataset mockDataset;
    private CadisDatasetCommand mockCadisDatasetCommand;

    @Before
    public void before() {

        this.mockDataset = mock(Dataset.class);
        mockCadisDatasetCommand = mock(CadisDatasetCommand.class);
    }

    @Test
    public void testSetIdentifierNeverCalled() {

        when(this.mockDataset.getIdentifier()).thenReturn(UUID.valueOf("3a69d760-7219-11e2-bcfd-0800200c9a66"));

        TemplateDatasetToCommandConverter converter = new TemplateDatasetToCommandConverter();
        converter.setIdentifier(mockDataset, mockCadisDatasetCommand);

        verifyZeroInteractions(this.mockCadisDatasetCommand);
    }

    @Test
    public void testSetNameWithCopyOfText() {

        when(this.mockDataset.getTitle()).thenReturn("Test Dataset Title");

        TemplateDatasetToCommandConverter converter = new TemplateDatasetToCommandConverter();
        converter.setName(mockDataset, mockCadisDatasetCommand);

        verifyZeroInteractions(this.mockCadisDatasetCommand);
    }
}
