package sgf.gateway.web.controllers.download;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.inventory.LogicalFile;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WgetScriptViewSelectorTest {

    private WgetScriptViewSelector selector;

    @Before
    public void setup() {
        selector = new WgetScriptViewSelector("certificateView", "noCertificateView");
    }

    @Test
    public void selectCertificateViewIfLogicalFileIsReadRestricted() {

        LogicalFile logicalFile = mock(LogicalFile.class);

        when(logicalFile.isReadRestricted()).thenReturn(true);

        List<LogicalFile> files = new ArrayList<LogicalFile>();
        files.add(logicalFile);

        String view = selector.select(files);

        assertThat(view, is("certificateView"));
    }

    @Test
    public void selectNoCertificateViewIfLogicalFileIsNotReadRestricted() {

        LogicalFile logicalFile = mock(LogicalFile.class);

        when(logicalFile.isReadRestricted()).thenReturn(false);

        List<LogicalFile> files = new ArrayList<LogicalFile>();
        files.add(logicalFile);

        String view = selector.select(files);

        assertThat(view, is("noCertificateView"));
    }

    @Test
    public void selectCertificateViewIfLogicalFilesMixedReadRestricted() {

        LogicalFile logicalFileRestricted = mock(LogicalFile.class);
        LogicalFile logicalFileNotRestricted = mock(LogicalFile.class);

        when(logicalFileRestricted.isReadRestricted()).thenReturn(true);
        when(logicalFileNotRestricted.isReadRestricted()).thenReturn(false);

        List<LogicalFile> files = new ArrayList<LogicalFile>();
        files.add(logicalFileRestricted);
        files.add(logicalFileNotRestricted);

        String view = selector.select(files);

        assertThat(view, is("certificateView"));
    }
}
