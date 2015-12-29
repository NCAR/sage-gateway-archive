package sgf.gateway.web.controllers.cadis.publish.command;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.metadata.CadisResolutionType;
import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetProgress;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;
import sgf.gateway.model.metadata.descriptive.TimePeriod;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;

public class DatasetToCommandConverterTest {

    private Dataset mockDataset;
    private CadisDatasetCommand mockCadisDatasetCommand;

    @Before
    public void before() {

        this.mockDataset = mock(Dataset.class);
        mockCadisDatasetCommand = mock(CadisDatasetCommand.class);
    }

    @Test
    public void testSetTitle() {

        when(this.mockDataset.getTitle()).thenReturn("Test Dataset Title");

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setTitle(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setTitle("Test Dataset Title");
    }

    @Test
    public void testSetDescription() {

        when(this.mockDataset.getDescription()).thenReturn("Test description from dataset.");

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setDescription(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setDescription("Test description from dataset.");
    }

    @Test
    public void testSetIdentifier() {

        when(this.mockDataset.getIdentifier()).thenReturn(UUID.valueOf("3a69d760-7219-11e2-bcfd-0800200c9a66"));

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setIdentifier(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setIdentifier(UUID.valueOf("3a69d760-7219-11e2-bcfd-0800200c9a66"));
    }

    @Test
    public void testSetDatasetProgress() {

        DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDescriptiveMetadata.getDatasetProgress()).thenReturn(DatasetProgress.COMPLETE);

        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockDescriptiveMetadata);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setDatasetProgress(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setDatasetProgress(DatasetProgress.COMPLETE);
    }

    @Test
    public void testSetLanguage() {

        DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDescriptiveMetadata.getLanguage()).thenReturn("English");

        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockDescriptiveMetadata);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setLanguage(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setLanguage("English");
    }

    @Test
    public void testSetResolutionTypes() {

        Collection<CadisResolutionType> resolutionTypes = new ArrayList<CadisResolutionType>();
        resolutionTypes.add(CadisResolutionType.RESOLUTION_1KM);
        resolutionTypes.add(CadisResolutionType.RESOLUTION_UNKNOWN);

        DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDescriptiveMetadata.getResolutionTypes()).thenReturn(resolutionTypes);

        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockDescriptiveMetadata);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setResolutionTypes(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setResolutionTypes(resolutionTypes);
    }

    @Test
    public void testSetSpatialDataTypes() {

        Collection<DataType> dataTypes = new ArrayList<DataType>();
        dataTypes.add(DataType.GRID);
        dataTypes.add(DataType.UNKNOWN);

        DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDescriptiveMetadata.getDataTypes()).thenReturn(dataTypes);

        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockDescriptiveMetadata);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setSpatialDataTypes(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setSpatialDataTypes(dataTypes);
    }

    @Test
    public void testSetTimeCoordinateAxis() {

        TimePeriod mockTimePeriod = mock(TimePeriod.class);
        DateTime startDateTime = new DateTime(2012, 2, 8, 0, 0, 0, 0);
        when(mockTimePeriod.getBegin()).thenReturn(startDateTime.toDate());
        DateTime endDateTime = new DateTime(2013, 3, 15, 0, 0, 0, 0);
        when(mockTimePeriod.getEnd()).thenReturn(endDateTime.toDate());

        DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockDescriptiveMetadata);

        when(mockDescriptiveMetadata.getTimePeriod()).thenReturn(mockTimePeriod);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setTimeCoordinateAxis(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setStartDate("2012-02-08");
        verify(this.mockCadisDatasetCommand).setEndDate("2013-03-15");
    }

    @Test
    public void testSetTimePeriodWithNull() {

        DatasetToCommandConverter converter = new DatasetToCommandConverter();

        DescriptiveMetadata descriptiveMetadata = mock(DescriptiveMetadata.class);
        when(mockDataset.getDescriptiveMetadata()).thenReturn(descriptiveMetadata);

        converter.setTimeCoordinateAxis(mockDataset, mockCadisDatasetCommand);

        verifyZeroInteractions(this.mockCadisDatasetCommand);
    }

    @Test
    public void testSetLongitudeCoordinateAxis() {

        GeographicBoundingBox mockBoundingBox = mock(GeographicBoundingBox.class);

        when(mockBoundingBox.getWestBoundLongitude()).thenReturn(-180.0);
        when(mockBoundingBox.getEastBoundLongitude()).thenReturn(180.0);
        when(mockBoundingBox.getSouthBoundLatitude()).thenReturn(-90.0);
        when(mockBoundingBox.getNorthBoundLatitude()).thenReturn(90.0);

        DescriptiveMetadata mockMetadata = mock(DescriptiveMetadata.class);
        when(mockMetadata.getGeographicBoundingBox()).thenReturn(mockBoundingBox);

        when(mockDataset.getDescriptiveMetadata()).thenReturn(mockMetadata);

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setGeographicBoundingBox(mockDataset, mockCadisDatasetCommand);

        verify(this.mockCadisDatasetCommand).setWesternLongitude("-180.0");
        verify(this.mockCadisDatasetCommand).setEasternLongitude("180.0");

        verify(this.mockCadisDatasetCommand).setNorthernLatitude("90.0");
        verify(this.mockCadisDatasetCommand).setSouthernLatitude("-90.0");
    }

    @Test
    public void testSetWithNullBoundingBox() {

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setGeographicBoundingBox(mockDataset, mockCadisDatasetCommand);

        verifyZeroInteractions(this.mockCadisDatasetCommand);
    }

    @Test
    public void testSetWithNullDescriptiveMetadata() {

        DatasetToCommandConverter converter = new DatasetToCommandConverter();
        converter.setGeographicBoundingBox(mockDataset, mockCadisDatasetCommand);

        verifyZeroInteractions(this.mockCadisDatasetCommand);
    }
}
