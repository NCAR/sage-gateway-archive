package sgf.gateway.integration.thredds.transform;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvDataset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeDatasetDoiTest {

    private InvDataset stubInvDataset;

    private ThreddsDatasetPayload payload;

    private NodeDatasetDoi node;

    @Before
    public void setup() {

        this.stubInvDataset = mock(InvDataset.class);
        this.payload = new ThreddsDatasetPayload();
        this.node = new NodeDatasetDoi();
    }

    @Test
    public void testDoiIsNull() {

        payload.setInvDataset(stubInvDataset);

        node.process(payload);

        assertThat(null, is(equalTo(payload.getDoi())));
    }

    @Test
    public void testDoiIsMisFormatted() {

        when(stubInvDataset.findProperty("doi")).thenReturn("10.5065/D6DF6P6C");

        payload.setInvDataset(stubInvDataset);

        node.process(payload);

        assertThat(null, is(equalTo(payload.getDoi())));
    }

    @Test
    public void testDoiIsCorrect() {

        when(stubInvDataset.findProperty("doi")).thenReturn("doi:10.5065/D6DF6P6C");

        payload.setInvDataset(stubInvDataset);

        node.process(payload);

        assertThat("doi:10.5065/D6DF6P6C", is(equalTo(payload.getDoi())));
    }

    @Test
    public void testDoiIsUpperCase() {

        when(stubInvDataset.findProperty("doi")).thenReturn("DOI:10.5065/D6DF6P6C");

        payload.setInvDataset(stubInvDataset);

        node.process(payload);

        assertThat(null, is(equalTo(payload.getDoi())));
    }
}
