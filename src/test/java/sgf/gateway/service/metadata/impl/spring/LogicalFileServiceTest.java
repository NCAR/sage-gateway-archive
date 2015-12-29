package sgf.gateway.service.metadata.impl.spring;


import org.junit.Before;
import org.junit.Test;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.LogicalFileService;
import sgf.gateway.service.publishing.dataset.file.TransferFileProcessor;

import java.io.File;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class LogicalFileServiceTest {

    private static final String FILE_NAME = "test_file.txt";

    private static final String DATASET_IDENTIFIER = "test_dataset";

    private DatasetRepository mockDatasetRepository;
    private TransferFileProcessor mockTransferFileProcessor;
    private Dataset mockDataset;
    private DatasetVersion mockDatasetVersion;
    private LogicalFile mockLogicalFile;

    @Before
    public void setup() {

        mockLogicalFile = mock(LogicalFile.class);

        mockDatasetVersion = mock(DatasetVersion.class);
        when(mockDatasetVersion.getLogicalFileByFileName(FILE_NAME)).thenReturn(mockLogicalFile);

        mockDataset = mock(Dataset.class);
        when(mockDataset.getCurrentDatasetVersion()).thenReturn(mockDatasetVersion);

        mockDatasetRepository = mock(DatasetRepository.class);
        when(mockDatasetRepository.getByShortName(DATASET_IDENTIFIER)).thenReturn(mockDataset);

        mockTransferFileProcessor = mock(TransferFileProcessor.class);
    }

    @Test
    public void testDeletableLogicalFileWithExistingFile() {

        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.delete()).thenReturn(true);

        when(mockLogicalFile.isDeletable()).thenReturn(true);
        when(mockLogicalFile.getFile()).thenReturn(mockFile);

        LogicalFileService service = new LogicalFileServiceImpl(mockDatasetRepository, mockTransferFileProcessor);
        service.deleteFile(DATASET_IDENTIFIER, FILE_NAME);

        verify(mockDatasetVersion).removeLogicalFile(mockLogicalFile);
        verify(mockFile).delete();
    }

    @Test
    public void testDeletableLogicalFileWithNonExistingFile() {

        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(false);

        when(mockLogicalFile.isDeletable()).thenReturn(true);
        when(mockLogicalFile.getFile()).thenReturn(mockFile);

        LogicalFileService service = new LogicalFileServiceImpl(mockDatasetRepository, mockTransferFileProcessor);
        service.deleteFile(DATASET_IDENTIFIER, FILE_NAME);

        verify(mockFile, never()).delete();
    }

    @Test(expected = FileNotDeletedException.class)
    public void testDeletableLogicalFileWithExistingFileButFileNotDeleted() {

        File mockFile = mock(File.class);
        when(mockFile.exists()).thenReturn(true);
        when(mockFile.delete()).thenReturn(false);

        when(mockLogicalFile.isDeletable()).thenReturn(true);
        when(mockLogicalFile.getFile()).thenReturn(mockFile);

        LogicalFileService service = new LogicalFileServiceImpl(mockDatasetRepository, mockTransferFileProcessor);
        service.deleteFile(DATASET_IDENTIFIER, FILE_NAME);
    }

    @Test
    public void testUnDeletableLogicalFile() {

        when(mockLogicalFile.isDeletable()).thenReturn(false);

        LogicalFileService service = new LogicalFileServiceImpl(mockDatasetRepository, mockTransferFileProcessor);
        service.deleteFile(DATASET_IDENTIFIER, FILE_NAME);

        verify(mockDatasetVersion, never()).removeLogicalFile(any(LogicalFile.class));
    }
}
