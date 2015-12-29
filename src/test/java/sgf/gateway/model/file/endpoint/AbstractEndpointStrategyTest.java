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

public class AbstractEndpointStrategyTest {

    @Test
    public void testFileAccessPointExists() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("scheme://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        AbstractEndpointStrategy strategy = new AbstractEndpointStrategy() {

            public URI getEndpoint(LogicalFile logicalFile) {

                return null;
            }

            public boolean endpointExists(LogicalFile logicalFile) {

                return true;
            }

        };

        boolean exists = strategy.fileAccessPointExists(mockLogicalFile, "scheme");

        assertThat(exists, equalTo(true));
    }

    @Test
    public void testFileAccessPointDoesNotExist() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("emehcs://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        AbstractEndpointStrategy strategy = new AbstractEndpointStrategy() {

            public URI getEndpoint(LogicalFile logicalFile) {

                return null;
            }

            public boolean endpointExists(LogicalFile logicalFile) {

                return true;
            }

        };

        boolean exists = strategy.fileAccessPointExists(mockLogicalFile, "scheme");

        assertThat(exists, equalTo(false));
    }

    @Test
    public void testMixedCaseSchemeName() {

        Set<FileAccessPoint> fileAccessPoints = new HashSet<FileAccessPoint>();

        FileAccessPoint mockFileAccessPoint = mock(FileAccessPoint.class);
        when(mockFileAccessPoint.getEndpoint()).thenReturn(URI.create("SrM://localhost.com/file.txt"));

        fileAccessPoints.add(mockFileAccessPoint);

        LogicalFile mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPoints()).thenReturn(fileAccessPoints);

        AbstractEndpointStrategy strategy = new AbstractEndpointStrategy() {

            public URI getEndpoint(LogicalFile logicalFile) {

                return null;
            }

            public boolean endpointExists(LogicalFile logicalFile) {

                return true;
            }

        };

        FileAccessPoint fileAccessPoint = strategy.getFileAccessPoint(mockLogicalFile, "sRm");

        assertThat(fileAccessPoint, equalTo(mockFileAccessPoint));
    }
}
