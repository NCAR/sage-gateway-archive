package sgf.gateway.model.metrics.factory.impl;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.dataaccess.FileAccessPoint;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metrics.FileDownload;

import java.net.URI;

import static org.mockito.Mockito.*;

public class AddFileTransformStepTest {

    private static final String FILE_LINEAGE = "lineage";
    private static final String FILE_VERSION = "version";
    private static final long FILE_SIZE = 12345L;
    private static final UUID FILE_ACCESS_POINT_IDENTIFIER = UUID.valueOf("b0290710-44be-11e2-bcfd-0800200c9a66");
    private static final URI FILE_ACCESS_POINT_URI = URI.create("http://localfile.com/test.nc");

    private static final UUID LOGICAL_FILE_IDENTIFIER = UUID.valueOf("a7629660-44bd-11e2-bcfd-0800200c9a66");
    private static final String FILE_NAME = "fileName";

    private FileDownload mockFileDownload;
    private LogicalFile mockLogicalFile;
    private FileAccessPoint mockFileAccessPoint;
    private LogicalFileRepository mockLogicalFileRepository;

    @Before
    public void setup() {

        mockFileDownload = mock(FileDownload.class);
        when(mockFileDownload.getFileAccessPointUri()).thenReturn("http://localfile.com/test.nc");

        mockFileAccessPoint = mock(FileAccessPoint.class);

        mockLogicalFile = mock(LogicalFile.class);
        when(mockLogicalFile.getFileAccessPointByURI(FILE_ACCESS_POINT_URI)).thenReturn(mockFileAccessPoint);

        mockLogicalFileRepository = mock(LogicalFileRepository.class);
    }

    @Test
    public void getLogicalFileFromRepositoryTest() {

        when(mockLogicalFileRepository.findLogicalFileByAccessPointURL(FILE_ACCESS_POINT_URI)).thenReturn(mockLogicalFile);


        AddFileTransformStep addFileTransformStep = new AddFileTransformStep(mockLogicalFileRepository);
        addFileTransformStep.transform(mockFileDownload);

        verify(mockLogicalFileRepository).findLogicalFileByAccessPointURL(FILE_ACCESS_POINT_URI);
    }

    @Test
    public void fileDownloadProperlySetTest() {

        when(mockLogicalFile.getIdentifier()).thenReturn(LOGICAL_FILE_IDENTIFIER);
        when(mockLogicalFile.getName()).thenReturn(FILE_NAME);
        when(mockLogicalFile.getSize()).thenReturn(FILE_SIZE);
        when(mockLogicalFile.getVersionIdentifier()).thenReturn(FILE_VERSION);
        when(mockLogicalFile.getLineageIdentifier()).thenReturn(FILE_LINEAGE);

        when(mockFileAccessPoint.getIdentifier()).thenReturn(FILE_ACCESS_POINT_IDENTIFIER);

        when(mockLogicalFileRepository.findLogicalFileByAccessPointURL(FILE_ACCESS_POINT_URI)).thenReturn(mockLogicalFile);


        AddFileTransformStep addFileTransformStep = new AddFileTransformStep(mockLogicalFileRepository);
        addFileTransformStep.transform(mockFileDownload);

        verify(mockFileDownload).setLogicalFileIdentifier(LOGICAL_FILE_IDENTIFIER);
        verify(mockFileDownload).setLogicalFileName(FILE_NAME);
        verify(mockFileDownload).setLogicalFileSize(12345L);
        verify(mockFileDownload).setLogicalFileVersionId(FILE_VERSION);
        verify(mockFileDownload).setLogicalFileLineageId(FILE_LINEAGE);

    }
}
