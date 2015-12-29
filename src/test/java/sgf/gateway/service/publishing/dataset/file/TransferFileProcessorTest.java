package sgf.gateway.service.publishing.dataset.file;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.web.multipart.MultipartFile;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.LogicalFileRepository;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.model.metadata.inventory.factory.LogicalFileFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class TransferFileProcessorTest {

    private File baseDataDirectory = new File("/base");
    private String datasetShortName = "shortName";
    private String fileName = "fileName";

    private LocalFileDirectoryStrategy directoryStrategy;
    private DatasetRepository datasetRepository;
    private LogicalFileFactory logicalFileFactory;

    @Before
    public void setup() {
        this.directoryStrategy = new LocalFileDirectoryStrategy(this.baseDataDirectory);
        this.datasetRepository = mock(DatasetRepository.class);
        this.logicalFileFactory = mock(LogicalFileFactory.class);
    }

    @Test
    public void testUpdateLogicalFile() {

        LogicalFileRepository logicalFileRepository = mock(LogicalFileRepository.class);
        UploadedFile uploadedFile = mock(UploadedFile.class);
        LogicalFile logicalFile = mock(LogicalFile.class);
        MultipartFile multipartFile = mock(MultipartFile.class);

        UUID logicalFileUUID = UUID.valueOf("9e5cd980-ab98-11e2-9e96-0800200c9a66");

        when(uploadedFile.getLogicalFileId()).thenReturn(logicalFileUUID);
        when(uploadedFile.getSource()).thenReturn(multipartFile);
        when(multipartFile.getSize()).thenReturn(10l);
        when(logicalFileRepository.get(logicalFileUUID)).thenReturn(logicalFile);

        TransferFileProcessor transferFileProcessor = new TransferFileProcessor(this.directoryStrategy, this.datasetRepository, logicalFileRepository, this.logicalFileFactory);

        transferFileProcessor.updateLogicalFile(uploadedFile);

        verify(logicalFile).setSize(10l);
    }

    @Test
    public void testGetLogicalFileWhenDoesNotExist() {

        Dataset dataset = mock(Dataset.class);
        File target = mock(File.class);
        LogicalFileRepository logicalFileRepository = mock(LogicalFileRepository.class);
        List<LogicalFile> logicalFiles = new ArrayList<LogicalFile>(0);

        when(logicalFileRepository.findByDatasetShortNameAndLogicalFileName(anyString(), anyString(), anyBoolean())).thenReturn(logicalFiles);
        when(dataset.getShortName()).thenReturn(this.datasetShortName);
        when(target.getName()).thenReturn(this.fileName);

        TransferFileProcessor transferFileProcessor = new TransferFileProcessor(this.directoryStrategy, this.datasetRepository, logicalFileRepository, this.logicalFileFactory);

        LogicalFile logicalFile = transferFileProcessor.getLogicalFile(dataset, target);

        assertNull(logicalFile);
    }

    @Test
    public void testGetLogicalFileWhenExists() {

        Dataset dataset = mock(Dataset.class);
        File target = mock(File.class);
        LogicalFileRepository logicalFileRepository = mock(LogicalFileRepository.class);
        LogicalFile logicalFile = mock(LogicalFile.class);

        List<LogicalFile> logicalFiles = new ArrayList<LogicalFile>(1);
        logicalFiles.add(logicalFile);

        when(logicalFileRepository.findByDatasetShortNameAndLogicalFileName(anyString(), anyString(), anyBoolean())).thenReturn(logicalFiles);
        when(dataset.getShortName()).thenReturn(this.datasetShortName);
        when(target.getName()).thenReturn(this.fileName);

        TransferFileProcessor transferFileProcessor = new TransferFileProcessor(this.directoryStrategy, this.datasetRepository, logicalFileRepository, this.logicalFileFactory);

        LogicalFile rtnLogicalFile = transferFileProcessor.getLogicalFile(dataset, target);

        assertNotNull(rtnLogicalFile);
        assertTrue(rtnLogicalFile == logicalFile);
    }
}
