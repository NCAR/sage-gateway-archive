package sgf.gateway.integration.metrics.tds.transformer.impl;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.tds.transformer.StartDateStrategy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.*;


public class FieldsTransformerTest {

    private static final String BYTES_SENT_KEY = "bytes";
    private static final String CONTENT_LENGTH_KEY = "length";
    private static final String START_DATE_KEY = "start";
    private static final String START_TIME_KEY = "time";
    private static final String DURATION_KEY = "duration";
    private static final String SCHEME_KEY = "scheme";
    private static final String HOST_KEY = "host";
    private static final String FILE_URI_STEM_KEY = "stem";
    private static final String STATUS_CODE_KEY = "status";
    private static final String OPENID_KEY = "openid";
    private static final String REMOTE_ADDRESS_KEY = "ip";
    private static final String USER_AGENT_KEY = "agent";
    private static final String REFERRER_KEY = "referrer";

    private FileDownloadPayload mockFileDownloadPayload;

	/* What a line from a metrics log file might look like:
	
	"2012-12-18 19:00:00 824.217 200 http 'tds.ucar.edu' /thredds/fileServer/datazone/cmip5_data/cmip5/output1/NCAR/CCSM4/piControl/mon/atmos/Amon/r1i1p1/v20120203/ua/ua_Amon_CCSM4_piControl_r1i1p1_120001-124912.nc '2256113488' 2256113488 199.26.254.200 'https://pcmdi9.llnl.gov/esgf-idp/openid/jennifer' 'Wget/1.11.4 Red Hat modified' -";

	*/

    @Before
    public void setup() {
        mockFileDownloadPayload = mock(FileDownloadPayload.class);
    }


    @Test
    public void bytesSentSuccessTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn("814440252");

        BytesSentFieldTransformer transformer = new BytesSentFieldTransformer(BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockTokens).get(BYTES_SENT_KEY);
        verify(mockFileDownloadPayload).setBytesSent(new Long(814440252));
    }

    @Test
    public void bytesSentNullTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn(null);

        BytesSentFieldTransformer transformer = new BytesSentFieldTransformer(BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setBytesSent(0L);
    }

    @Test
    public void startDateTest() throws ParseException {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(START_DATE_KEY)).thenReturn("2012-12-17");
        when(mockTokens.get(START_TIME_KEY)).thenReturn("21:02:26");

        StartDateStrategy mockTDSMetricsStartDateStrategy = mock(StartDateStrategy.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date expectedStartDate = sdf.parse("2012-12-17 21:02:26 +0000");

        when(mockTDSMetricsStartDateStrategy.extractStartDate("2012-12-17", "21:02:26")).thenReturn(expectedStartDate);

        StartDateFieldTransformer transformer = new StartDateFieldTransformer(START_DATE_KEY, START_TIME_KEY, mockTDSMetricsStartDateStrategy);

        mockFileDownloadPayload = transformer.transform(mockFileDownloadPayload, mockTokens);

        //assertThat(mockFileDownloadPayload.getEndDate(), Is.is(expectedStartDate));
        verify(mockFileDownloadPayload).setStartDate(expectedStartDate);
    }

    @Test
    public void endDateTest() throws ParseException {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(START_DATE_KEY)).thenReturn("2012-12-17");
        when(mockTokens.get(START_TIME_KEY)).thenReturn("21:02:26");
        when(mockTokens.get(DURATION_KEY)).thenReturn("173.858");

        StartDateStrategy mockTDSMetricsStartDateStrategy = mock(StartDateStrategy.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date expectedStartDate = sdf.parse("2012-12-17 21:02:26 +0000");

        when(mockTDSMetricsStartDateStrategy.extractStartDate("2012-12-17", "21:02:26")).thenReturn(expectedStartDate);

        EndDateFieldTransformer transformer = new EndDateFieldTransformer(DURATION_KEY, START_DATE_KEY, START_TIME_KEY, mockTDSMetricsStartDateStrategy);

        mockFileDownloadPayload = transformer.transform(mockFileDownloadPayload, mockTokens);

        SimpleDateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        Date expectedEndDate = endDateFormat.parse("2012-12-17 21:05:19.858 +0000");

        verify(mockFileDownloadPayload).setEndDate(expectedEndDate);
    }

    @Test
    public void fileURITest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(SCHEME_KEY)).thenReturn("http");
        when(mockTokens.get(HOST_KEY)).thenReturn("tds.ucar.edu");
        when(mockTokens.get(FILE_URI_STEM_KEY)).thenReturn("/thredds/fileServer/datazone/cmip5_data/cmip5/output1/NCAR/CCSM4/piControl/mon/atmos/Amon/r1i1p1/v20120203/ua/ua_Amon_CCSM4_piControl_r1i1p1_120001-124912.nc");

        FileURIFieldTransformer transformer = new FileURIFieldTransformer(SCHEME_KEY, HOST_KEY, FILE_URI_STEM_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        String expectedFileURI = "http://tds.ucar.edu/thredds/fileServer/datazone/cmip5_data/cmip5/output1/NCAR/CCSM4/piControl/mon/atmos/Amon/r1i1p1/v20120203/ua/ua_Amon_CCSM4_piControl_r1i1p1_120001-124912.nc";

        verify(mockFileDownloadPayload).setFileURI(expectedFileURI);
    }

    @Test
    public void statusCodeTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(STATUS_CODE_KEY)).thenReturn("200");

        StatusCodeFieldTransformer transformer = new StatusCodeFieldTransformer(STATUS_CODE_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setStatus("200");
    }

    @Test
    public void openIdTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(OPENID_KEY)).thenReturn("https://pcmdi9.llnl.gov/esgf-idp/openid/jennifer");

        OpenIdFieldTransformer transformer = new OpenIdFieldTransformer(OPENID_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setOpenId("https://pcmdi9.llnl.gov/esgf-idp/openid/jennifer");
    }

    @Test
    public void remoteAddressTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(REMOTE_ADDRESS_KEY)).thenReturn("142.104.57.148");

        RemoteAddressFieldTransformer transformer = new RemoteAddressFieldTransformer(REMOTE_ADDRESS_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setRemoteAddress("142.104.57.148");
    }

    @Test
    public void userAgentTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(USER_AGENT_KEY)).thenReturn("Wget/1.12 (linux-gnu)");

        UserAgentFieldTransformer transformer = new UserAgentFieldTransformer(USER_AGENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setUserAgent("Wget/1.12 (linux-gnu)");
    }

    @Test
    public void completedValueTrueTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(CONTENT_LENGTH_KEY)).thenReturn("814440252");
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn("814440252");

        CompletedFieldTransformer transformer = new CompletedFieldTransformer(CONTENT_LENGTH_KEY, BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setCompleted(true);
    }

    @Test
    public void completedValueFalseTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(CONTENT_LENGTH_KEY)).thenReturn("814440252");
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn("814440251");

        CompletedFieldTransformer transformer = new CompletedFieldTransformer(CONTENT_LENGTH_KEY, BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload, never()).setCompleted(true);  // left with default of "false"
    }

    @Test
    public void completedValueZeroTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(CONTENT_LENGTH_KEY)).thenReturn("0");
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn("0");

        CompletedFieldTransformer transformer = new CompletedFieldTransformer(CONTENT_LENGTH_KEY, BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload, never()).setCompleted(true);  // left with default of "false"
    }
}
