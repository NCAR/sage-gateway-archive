package sgf.gateway.integration.metrics.gridftp.transformer.impl;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.metrics.FileDownloadPayload;
import sgf.gateway.integration.metrics.gridftp.transformer.HostnameTransformer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


public class FieldsTransformerTest {

    private static final String START_DATE_KEY = "START";
    private static final String END_DATE_KEY = "END";
    private static final String BYTES_SENT_KEY = "NBYTES";
    private static final String SCHEMA_KEY = "SCHEMA";
    private static final String FILE_URI_STEM_KEY = "FILE";
    private static final String PORT_KEY = "CONFID";
    private static final String STATUS_CODE_KEY = "CODE";
    private static final String USERDN_KEY = "USERDN";
    private static final String HOST_KEY = "HOSTNAME";

    private FileDownloadPayload mockFileDownloadPayload;

	/* What a line from a (new) gridftp metrics log file might look like:
	
	HOSTNAME=vetsman.ucar.edu START=20110216193159.532499 END=20110216193217.971458 VER="3.23 (gcc64dbg, 1278696115-80) [Globus Toolkit 5.0.2]" BUFFER=87380 BLOCK=1048576 NBYTES=19706408 STREAMS=1 STRIPES=1 TYPE=RETR CODE=226 FILE=/datazone/esg-cdp/xserve/ccsm/csm/b30.004/atm/proc/tseries/monthly/b30.004.cam2.h0.CLDHGH.0500-01_cat_0549-12.nc CLIENTIP=128.117.65.100 DATAIP=128.117.65.100 USER=globus USERDN=/O=ESG-CET/OU=NCAR/OU=simpleCA-vetswebprod.ucar.edu/CN=https://www.earthsystemgrid.org/myopenid/enienhouse CONFID=2811 DSI=file EM=customgsiauthzinterface SCHEMA=gsiftp APP=CoG APPVER=1.7.0

	 */

    @Before
    public void setup() {
        mockFileDownloadPayload = mock(FileDownloadPayload.class);
    }

    @Test
    public void bytesSentSuccessTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn("19706408");

        BytesSentFieldTransformer transformer = new BytesSentFieldTransformer(BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockTokens).get(BYTES_SENT_KEY);
        verify(mockFileDownloadPayload).setBytesSent(new Long(19706408));
    }

    @Test(expected = NumberFormatException.class)
    public void bytesSentNullTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(BYTES_SENT_KEY)).thenReturn(null);

        BytesSentFieldTransformer transformer = new BytesSentFieldTransformer(BYTES_SENT_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);
    }

    @Test
    public void startDateTest() throws ParseException {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(START_DATE_KEY)).thenReturn("20110216193159.532499");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date expectedStartDate = sdf.parse("2011-02-16 19:31:59 +0000");

        StartDateFieldTransformer transformer = new StartDateFieldTransformer(START_DATE_KEY);

        mockFileDownloadPayload = transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setStartDate(expectedStartDate);
    }

    @Test
    public void endDateTest() throws ParseException {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(END_DATE_KEY)).thenReturn("20110216193217.971458");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
        Date expectedEndDate = sdf.parse("2011-02-16 19:32:17 +0000");

        EndDateFieldTransformer transformer = new EndDateFieldTransformer(END_DATE_KEY);

        mockFileDownloadPayload = transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setEndDate(expectedEndDate);
    }

    @Test
    public void statusCodeTest() {

        Map<String, String> mockTokens = mock(Map.class);

        when(mockTokens.get(STATUS_CODE_KEY)).thenReturn("226");

        StatusCodeFieldTransformer transformer = new StatusCodeFieldTransformer(STATUS_CODE_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setStatus("226");
    }

    @Test
    public void hostnameTransformerTest() {

        HostnameTransformer transformer = new HostnameTransformer(HOST_KEY);

        String hostResult = transformer.transform("....u.\'M\\&AHOSTNAME=vetsman.ucar.edu");

        String expectedHost = "HOSTNAME=vetsman.ucar.edu";

        assertThat(hostResult, Is.is(expectedHost));
    }

    @Test
    public void fileURITest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(SCHEMA_KEY)).thenReturn("gsiftp");
        when(mockTokens.get(HOST_KEY)).thenReturn("vetsdataprod.ucar.edu");
        when(mockTokens.get(PORT_KEY)).thenReturn("2811");
        when(mockTokens.get(FILE_URI_STEM_KEY)).thenReturn("//datazone/esg-cdp/xserve/ccsm/csm/b30.004/atm/proc/tseries/monthly/b30.004.cam2.h0.CLDHGH.0500-01_cat_0549-12.nc");

        FileURIStrategy fileURIStrategy = new FileURIStrategy("gsiftp", "vetsman.ucar.edu", PORT_KEY, FILE_URI_STEM_KEY);

        FileURIFieldTransformer transformer = new FileURIFieldTransformer(fileURIStrategy);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        String expectedFileURI = "gsiftp://vetsman.ucar.edu:2811//datazone/esg-cdp/xserve/ccsm/csm/b30.004/atm/proc/tseries/monthly/b30.004.cam2.h0.CLDHGH.0500-01_cat_0549-12.nc";

        verify(mockFileDownloadPayload).setFileURI(expectedFileURI);
    }

    @Test
    public void openIdTest() {

        Map<String, String> mockTokens = mock(Map.class);
        when(mockTokens.get(USERDN_KEY)).thenReturn("/O=ESG-CET/OU=NCAR/OU=simpleCA-vetswebprod.ucar.edu/CN=https://www.earthsystemgrid.org/myopenid/enienhouse");

        OpenIdFieldTransformer transformer = new OpenIdFieldTransformer(USERDN_KEY);

        transformer.transform(mockFileDownloadPayload, mockTokens);

        verify(mockFileDownloadPayload).setOpenId("https://www.earthsystemgrid.org/myopenid/enienhouse");
    }
}
