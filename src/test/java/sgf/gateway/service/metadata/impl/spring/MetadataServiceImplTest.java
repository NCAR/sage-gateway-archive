package sgf.gateway.service.metadata.impl.spring;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.dao.metadata.MetadataRepository;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MetadataServiceImplTest {

    private MetadataServiceImpl testMetadataServiceInstance;
    private MetadataRepository mockMetadataRepository;
    private DatasetRepository mockDatasetRepository;
    private UUID presentFileID1 = new UUID("0a4ac82d-8450-11e3-bcdb-00c0f03d5b7c");
    private UUID presentFileID2 = new UUID("0a965a60-dd29-11e3-85a2-00c0f03d5b7c");
    private LogicalFile presentFile1, presentFile2;

    private UUID missingFileID1 = new UUID("0aae1287-3a7d-11e3-8af4-00c0f03d5b7c");
    private UUID missingFileID2 = new UUID("0ae4c8b5-8451-11e3-bcdb-00c0f03d5b7c");

    private ArrayList<UUID> presentFileIDs, missingFileIDs, desiredFileIDs;
    private ArrayList<LogicalFile> foundFiles;


    @Before
    public void setup() {
        this.presentFileIDs = new ArrayList<UUID>();
        this.presentFileIDs.add(presentFileID1);
        this.presentFileIDs.add(presentFileID2);

        this.missingFileIDs = new ArrayList<UUID>();
        this.missingFileIDs.add(missingFileID1);
        this.missingFileIDs.add(missingFileID2);

        this.desiredFileIDs = new ArrayList<UUID>();
        this.desiredFileIDs.addAll(presentFileIDs);
        this.desiredFileIDs.addAll(missingFileIDs);

        this.presentFile1 = mock(LogicalFile.class);
        this.presentFile2 = mock(LogicalFile.class);
        when(presentFile1.getIdentifier()).thenReturn(presentFileID1);
        when(presentFile2.getIdentifier()).thenReturn(presentFileID2);
        this.foundFiles = new ArrayList<LogicalFile>();
        this.foundFiles.add(presentFile1);
        this.foundFiles.add(presentFile2);

        this.mockMetadataRepository = mock(MetadataRepository.class);
        when(mockMetadataRepository.findLogicalFileById(desiredFileIDs)).thenReturn(foundFiles);
        when(mockMetadataRepository.findLogicalFileById(presentFileIDs)).thenReturn(foundFiles);

        this.mockDatasetRepository = mock(DatasetRepository.class);
        this.testMetadataServiceInstance = new MetadataServiceImpl(mockMetadataRepository, mockDatasetRepository);


    }

    @Test(expected = ObjectNotFoundException.class)
    public void findMissingFilesCausesException() throws Exception {

        List<LogicalFile> foundFiles = testMetadataServiceInstance.findLogicalFileById(desiredFileIDs);
    }

    @Test
    public void findPresentFilesReturnsLogicalFiles() {

        List<LogicalFile> foundFiles = testMetadataServiceInstance.findLogicalFileById(presentFileIDs);
        assertThat(foundFiles, Matchers.<List<LogicalFile>>equalTo(this.foundFiles));
    }
}