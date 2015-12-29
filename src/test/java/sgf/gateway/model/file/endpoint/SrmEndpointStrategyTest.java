package sgf.gateway.model.file.endpoint;

import org.junit.Test;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SrmEndpointStrategyTest {

    @Test
    public void testEndpointExists() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("srm://localhost:8080/"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);

        when(mockFileAccessPoint.getLogicalFile()).thenReturn(mockLogicalFile);

        SrmEndpointStrategy strategy = new SrmEndpointStrategy();

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(true));
    }

    @Test
    public void testEndpointDoesNotExist() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("http://localhost:8080/"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);
        when(mockLogicalFile.getSize()).thenReturn(12345L);

        when(mockFileAccessPoint.getLogicalFile()).thenReturn(mockLogicalFile);

        SrmEndpointStrategy strategy = new SrmEndpointStrategy();

        boolean exists = strategy.endpointExists(mockLogicalFile);

        assertThat(exists, equalTo(false));
    }

    @Test
    public void testGetEndpointFromExternalSource() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("srm://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        SrmEndpointStrategy strategy = new SrmEndpointStrategy();

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(URI.create("srm://localhost.com/file.txt")));
    }

    @Test
    public void testOnlyHttpEndpointAvailable() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("http://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        SrmEndpointStrategy strategy = new SrmEndpointStrategy();

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

        SrmEndpointStrategy strategy = new SrmEndpointStrategy();

        URI endpoint = strategy.getEndpoint(mockLogicalFile);

        assertThat(endpoint, equalTo(null));
    }
}
