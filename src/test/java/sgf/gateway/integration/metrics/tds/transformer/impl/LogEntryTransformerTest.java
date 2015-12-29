package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.FieldSpecifiedStrategy;
import sgf.gateway.integration.metrics.tds.transformer.FieldTransformer;
import sgf.gateway.integration.metrics.tds.transformer.LogEntryTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class LogEntryTransformerTest {

    private LogEntryTokenizer mockTokenizer;
    private FieldSpecifiedStrategy mockFieldSpecifiedStrategy;

    private List<FieldTransformer> fieldTransformers;

    BytesSentFieldTransformer mockBytesSentFieldTransformer;
    StartDateFieldTransformer mockStartDateFieldTransformer;
    FileURIFieldTransformer mockFileURIFieldTransformer;
    OpenIdFieldTransformer mockOpenIdFieldTransformer;
    RemoteAddressFieldTransformer mockRemoteAddressFieldTransformer;
    StatusCodeFieldTransformer mockStatusCodeFieldTransformer;
    CompletedFieldTransformer mockCompletedFieldTransformer;
    UserAgentFieldTransformer mockUserAgentFieldTransformer;

    String tdsMetrics;

	/* What a line from a metrics log file might look like:
	
	"2012-12-18 19:00:00 824.217 200 http 'tds.ucar.edu' /thredds/fileServer/datazone/cmip5_data/cmip5/output1/NCAR/CCSM4/piControl/mon/atmos/Amon/r1i1p1/v20120203/ua/ua_Amon_CCSM4_piControl_r1i1p1_120001-124912.nc '2256113488' 2256113488 199.26.254.200 'https://pcmdi9.llnl.gov/esgf-idp/openid/jennifer' 'Wget/1.11.4 Red Hat modified' -";

	*/

    @Before
    public void setup() {

        mockTokenizer = mock(LogEntryTokenizer.class);
        mockFieldSpecifiedStrategy = mock(FieldSpecifiedStrategy.class);

        mockBytesSentFieldTransformer = mock(BytesSentFieldTransformer.class);
        mockStartDateFieldTransformer = mock(StartDateFieldTransformer.class);
        mockOpenIdFieldTransformer = mock(OpenIdFieldTransformer.class);
        mockRemoteAddressFieldTransformer = mock(RemoteAddressFieldTransformer.class);
        mockStatusCodeFieldTransformer = mock(StatusCodeFieldTransformer.class);
        mockCompletedFieldTransformer = mock(CompletedFieldTransformer.class);
        mockUserAgentFieldTransformer = mock(UserAgentFieldTransformer.class);

        fieldTransformers = new ArrayList<FieldTransformer>();
        fieldTransformers.add(mockBytesSentFieldTransformer);
        fieldTransformers.add(mockStartDateFieldTransformer);
        fieldTransformers.add(mockOpenIdFieldTransformer);
        fieldTransformers.add(mockRemoteAddressFieldTransformer);
        fieldTransformers.add(mockStatusCodeFieldTransformer);
        fieldTransformers.add(mockCompletedFieldTransformer);
        fieldTransformers.add(mockUserAgentFieldTransformer);

        tdsMetrics = "2012-12-18 19:00:00 824.217 200 http 'tds.ucar.edu' /thredds/fileServer/datazone/cmip5_data/cmip5/output1/NCAR/CCSM4/piControl/mon/atmos/Amon/r1i1p1/v20120203/ua/ua_Amon_CCSM4_piControl_r1i1p1_120001-124912.nc '2256113488' 2256113488 199.26.254.200 'https://pcmdi9.llnl.gov/esgf-idp/openid/jennifer' 'Wget/1.11.4 Red Hat modified' -";
    }

    // Basically just verifies that each individual transformer has its transform() method called.
    @Test
    public void transformTest() {

        LogEntryTransformerImpl transformer = new LogEntryTransformerImpl(mockTokenizer, mockFieldSpecifiedStrategy, fieldTransformers);

        transformer.transform(tdsMetrics);

        verify(mockBytesSentFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockStartDateFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockOpenIdFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockRemoteAddressFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockStatusCodeFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockCompletedFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
        verify(mockUserAgentFieldTransformer).transform(any(FileDownloadPayload.class), any(Map.class));
    }

}
