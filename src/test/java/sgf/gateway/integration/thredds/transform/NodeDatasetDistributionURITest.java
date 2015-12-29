package sgf.gateway.integration.thredds.transform;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import thredds.catalog.InvAccess;
import thredds.catalog.InvDataset;
import thredds.catalog.InvService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeDatasetDistributionURITest {

    private static final String validServiceName = "codiac_order";
    private static final String invalidServiceName = "codiac_tape";

    private static final String mockBaseURI = "http://fake.website.com?urlpath=";
    private static final String mockUrlPath = "599.607";
    private static final String mockDescription = "This is a fake URL description";

    private InvService mockInvService = mock(InvService.class);
    private InvAccess mockInvAccess = mock(InvAccess.class);
    private InvDataset mockInvDataset = mock(InvDataset.class);
    private ThreddsDatasetPayload payload = new ThreddsDatasetPayload();


    @Before
    public void setup() {
        when(mockInvService.getBase()).thenReturn(mockBaseURI);
        when(mockInvService.getDescription()).thenReturn(mockDescription);

        when(mockInvAccess.getService()).thenReturn(mockInvService);
        when(mockInvAccess.getUrlPath()).thenReturn(mockUrlPath);

        List<InvAccess> accessList = new ArrayList<InvAccess>();

        accessList.add(mockInvAccess);

        when(mockInvDataset.getAccess()).thenReturn(accessList);

        payload.setInvDataset(mockInvDataset);
    }

    @Test
    public void validAccessServiceReturnsValidURI() throws Exception {

        when(mockInvService.getName()).thenReturn(validServiceName);

        NodeDatasetDistributionURI node = new NodeDatasetDistributionURI();
        node.process(payload);

        String distributionString = payload.getDistributionURI().toString();
        assertThat(distributionString, is(mockBaseURI + mockUrlPath));
        assertThat(payload.getDistributionText(), is(mockDescription));
    }

    @Test
    public void invalidAccessServiceReturnsNullURI() throws Exception {

        when(mockInvService.getName()).thenReturn(invalidServiceName);

        NodeDatasetDistributionURI node = new NodeDatasetDistributionURI();
        node.process(payload);

        assertNull(payload.getDistributionURI());
        assertNull(payload.getDistributionText());
    }
}