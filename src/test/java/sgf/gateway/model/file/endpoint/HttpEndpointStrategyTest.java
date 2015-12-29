package sgf.gateway.model.file.endpoint;

import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HttpEndpointStrategyTest {

    @Test
    public void testEndpointExistsNullDiskLocation() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("http://localhost:8080/"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);
        when(mockLogicalFile.getDiskLocation()).thenReturn(null);

        when(mockFileAccessPoint.getLogicalFile()).thenReturn(mockLogicalFile);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(true));
    }

    @Test
    public void testEndpointDoesNotExistNullDiskLocation() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("other://localhost:8080/"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);
        when(mockLogicalFile.getDiskLocation()).thenReturn(null);

        when(mockFileAccessPoint.getLogicalFile()).thenReturn(mockLogicalFile);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(false));
    }

    @Test
    public void testEndpointExistsDiskLocationOnly() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);
        when(mockLogicalFile.getDiskLocation()).thenReturn("/local/disk/location/file.nc");

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(true));
    }

    @Test
    public void testEndpointDoesNotExist() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);
        when(mockLogicalFile.getDiskLocation()).thenReturn(null);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(false));
    }

    @Test
    public void testGetEndpointFromExternalSource() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("http://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(URI.create("http://localhost.com/file.txt")));
    }

    @Test
    public void testGetEndpointFromDisk() {

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getBaseSecureURL()).thenReturn(URI.create("https://localhost.com/"));

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getIdentifier()).thenReturn(UUID.valueOf("900f23b0-f57b-11e2-b778-0800200c9a66"));
        when(mockLogicalFile.getDiskLocation()).thenReturn("/localdisk/files/file.txt");

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(mockGateway);

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(URI.create("https://localhost.com/download/fileDownload.htm?logicalFileId=900f23b0-f57b-11e2-b778-0800200c9a66")));
    }

    @Test
    public void testOnlySRMEndpointAvailable() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("srm://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(null));
    }

    @Test
    public void testOnlyGridftpEndpointAvailable() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("gsiftp://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(null);

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(null));
    }

    @Test
    public void testBuildHttpUriNormalization() {

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getBaseSecureURL()).thenReturn(URI.create("https://localhost.com//"));

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getIdentifier()).thenReturn(UUID.valueOf("900f23b0-f57b-11e2-b778-0800200c9a66"));

        HttpEndpointStrategy strategy = new HttpEndpointStrategy(mockGateway);

        URI endpoint = strategy.buildHttpUri(mockLogicalFile);

        assertThat(endpoint, equalTo(URI.create("https://localhost.com/download/fileDownload.htm?logicalFileId=900f23b0-f57b-11e2-b778-0800200c9a66")));
    }
}
