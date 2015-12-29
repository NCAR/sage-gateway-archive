package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.publishing.thredds.transform.impl.DatasetMetadataGeospatialCoverageTransformer.GeographicPoint;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class DatasetMetadataGeospatialCoverageTransformerTest {

    DatasetMetadataGeospatialCoverageTransformer geospatialTransformer;
    InvDataset mockInvDataset = mock(InvDataset.class);
    Dataset mockDataset = mock(Dataset.class);
    DescriptiveMetadata mockDescriptiveMetadata = mock(DescriptiveMetadata.class);

    ThreddsMetadata.GeospatialCoverage mockGeoCoverage = mock(ThreddsMetadata.GeospatialCoverage.class);
    String locationName = "Arctic";

    @Before
    public void setUp() throws Exception {

        when(mockInvDataset.getGeospatialCoverage()).thenReturn(mockGeoCoverage);

        geospatialTransformer = new DatasetMetadataGeospatialCoverageTransformer();
    }

    @Test
    public void testGetLatitudeBounds() {

        GeographicPoint latitudeBounds = geospatialTransformer.getLatitudeBounds(mockGeoCoverage);

        assertNotNull(latitudeBounds);
        //assertThat(mockLocation, is(location));
    }

    @Test
    public void testGetLongitudeBounds() {

        GeographicPoint longitudeBounds = geospatialTransformer.getLongitudeBounds(mockGeoCoverage);

        assertNotNull(longitudeBounds);
        //assertThat(mockLocation, is(location));
    }

    @Test
    public void testCalculateEnd() {

        Double start = 0d;
        Double size = 2d;

        Double end = geospatialTransformer.calculateEnd(start, size);
        assertThat(end, is(2d));

    }

    // TODO: I am not sure what can be tested here.
    @Test
    public void testTransform() {

        DatasetMetadataGeospatialCoverageTransformer mockTransformer = spy(new DatasetMetadataGeospatialCoverageTransformer());
        mockTransformer.transform(mockInvDataset, mockDescriptiveMetadata);
        verify(mockTransformer).getLatitudeBounds(mockGeoCoverage);
        verify(mockTransformer).getLongitudeBounds(mockGeoCoverage);

//		GeographicPoint mockLatitudeBounds = mock (GeographicPoint.class);
//		GeographicPoint mockLongitudeBounds = mock (GeographicPoint.class);
//		
//		when(mockLongitudeBounds.getStart()).thenReturn(0d);
//		when(mockLongitudeBounds.getEnd()).thenReturn(120d);
//		when(mockLatitudeBounds.getStart()).thenReturn(-30d);
//		when(mockLatitudeBounds.getEnd()).thenReturn(60d);
//
//		verify(mockDescriptiveMetadata).setGeographicBoundingBox(0d, 120d, -30d, 60d);
    }


}
