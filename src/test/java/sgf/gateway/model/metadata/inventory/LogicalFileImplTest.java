package sgf.gateway.model.metadata.inventory;

import org.junit.Test;
import sgf.gateway.model.metadata.dataaccess.FileAccessPointImpl;

import java.io.File;
import java.net.URI;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogicalFileImplTest {

    private static final String ALGORITHM_MD5 = "MD5";

    private static final String CHECKSUM_MD5 = "foo";

    @Test
    public void testIsDeletableNullLocation() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        boolean deletable = logicalFile.isDeletable();

        assertThat(deletable, is(false));
    }

    @Test
    public void testIsDeletableLocation() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();
        logicalFile.setDiskLocation("testfile");

        boolean deletable = logicalFile.isDeletable();

        assertThat(deletable, is(true));
    }

    @Test
    public void testExtractFilePath() {

        URI localFilePath = URI.create("file:/datazone/data/testfile.txt");

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        assertThat(logicalFile.extractFilePath(localFilePath), is(equalToIgnoringCase("/datazone/data/testfile.txt")));
    }

    @Test
    public void testExtractFilePathWithDoubleSlash() {

        URI localFilePath = URI.create("file:///datazone/data/testfile.txt");

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        assertThat(logicalFile.extractFilePath(localFilePath), is(equalToIgnoringCase("/datazone/data/testfile.txt")));
    }

    @Test
    public void testAddExternalFileAccessPoint() {

        URI remoteFilePath = URI.create("http://test");

        FileAccessPointImpl fap = mock(FileAccessPointImpl.class);
        when(fap.getEndpoint()).thenReturn(remoteFilePath);

        LogicalFile logicalFile = new LogicalFileImpl();
        logicalFile.addFileAccessPoint(fap);

        assertThat(logicalFile.getFile(), is(nullValue()));
    }

    @Test
    public void testGetNullDiskLocation() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        File fileOnDisk = logicalFile.getFile();

        assertThat(fileOnDisk, is(nullValue()));
    }

    @Test
    public void testGetDiskLoaction() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        logicalFile.setDiskLocation("testfile");

        File fileOnDisk = logicalFile.getFile();

        assertThat(fileOnDisk.getName(), is(equalToIgnoringCase("testfile")));
    }

    /**
     * Verify adding checksums to the logicalfile.
     */
    @Test
    public void testCreateChecksum() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        // Create a checksum.
        logicalFile.addChecksum(ALGORITHM_MD5, CHECKSUM_MD5);

        assertThat(logicalFile.getMd5Checksum(), is(equalToIgnoringCase(CHECKSUM_MD5)));

    }

    @Test(expected = ChecksumMismatchException.class)
    public void testUnexpectedChecksumThrowsException() {

        LogicalFileImpl logicalFile = new LogicalFileImpl();

        logicalFile.setMd5Checksum(CHECKSUM_MD5);
        String differentChecksum = CHECKSUM_MD5 + CHECKSUM_MD5;

        logicalFile.setMd5Checksum(differentChecksum);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CallingAddChecksumWithBadArgumentThrowsException() {

        String badAlgorithmName = "FOO";
        LogicalFileImpl logicalFile = new LogicalFileImpl();
        logicalFile.addChecksum(badAlgorithmName, CHECKSUM_MD5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CallingGetChecksumWithBadArgumentThrowsException() {

        String badAlgorithmName = "FOO";
        LogicalFileImpl logicalFile = new LogicalFileImpl();
        String checkSumValue = logicalFile.getChecksum(badAlgorithmName);
    }
}
