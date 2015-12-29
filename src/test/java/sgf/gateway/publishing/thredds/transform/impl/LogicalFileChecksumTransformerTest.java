package sgf.gateway.publishing.thredds.transform.impl;

import org.junit.Test;
import sgf.gateway.model.metadata.inventory.LogicalFile;
import thredds.catalog.InvDataset;
import thredds.catalog.InvProperty;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class LogicalFileChecksumTransformerTest {

    @Test
    public void testValidChecksum() {

        InvDataset mockInvDatasetFile = mock(InvDataset.class);
        when(mockInvDatasetFile.getProperties()).thenReturn(new ArrayList<InvProperty>());
        when(mockInvDatasetFile.findProperty("checksum_type")).thenReturn("MD5");
        when(mockInvDatasetFile.findProperty("checksum")).thenReturn("73f48840b60ab6da68b03acd322445ee");

        LogicalFile mockLogicalFile = mock(LogicalFile.class);

        LogicalFileChecksumTransformer transformer = new LogicalFileChecksumTransformer();

        transformer.transform(mockInvDatasetFile, mockLogicalFile);

        verify(mockLogicalFile).removeAllChecksums();
        verify(mockLogicalFile).addChecksum("MD5", "73f48840b60ab6da68b03acd322445ee");
    }

    @Test
    public void testMissingChecksumType() {

        InvDataset mockInvDatasetFile = mock(InvDataset.class);
        when(mockInvDatasetFile.getProperties()).thenReturn(new ArrayList<InvProperty>());
        when(mockInvDatasetFile.findProperty("checksum")).thenReturn("73f48840b60ab6da68b03acd322445ee");

        LogicalFile mockLogicalFile = mock(LogicalFile.class);

        LogicalFileChecksumTransformer transformer = new LogicalFileChecksumTransformer();

        transformer.transform(mockInvDatasetFile, mockLogicalFile);

        verify(mockLogicalFile).removeAllChecksums();
        verifyNoMoreInteractions(mockLogicalFile);
    }

    @Test
    public void testMissingChecksumValue() {

        InvDataset mockInvDatasetFile = mock(InvDataset.class);
        when(mockInvDatasetFile.getProperties()).thenReturn(new ArrayList<InvProperty>());
        when(mockInvDatasetFile.findProperty("checksum_type")).thenReturn("MD5");

        LogicalFile mockLogicalFile = mock(LogicalFile.class);

        LogicalFileChecksumTransformer transformer = new LogicalFileChecksumTransformer();

        transformer.transform(mockInvDatasetFile, mockLogicalFile);

        verify(mockLogicalFile).removeAllChecksums();
        verifyNoMoreInteractions(mockLogicalFile);
    }
}
