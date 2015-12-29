package sgf.gateway.service.yadis.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.xrds.XrdsServiceEndpoint;
import org.openid4java.discovery.yadis.YadisResolver;
import org.openid4java.discovery.yadis.YadisResult;
import org.openid4java.util.HttpCache;

import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteYadisProxyImplTest {

    private static final String OPENID_TESTUSER = "https://test.com/openid/testuser";

    private static final String SAML_ATTRIBUTE_ENDPOINT = "https://test.com/remoteSamlAttributeEndpoint";

    private static final String SERVICE_TYPE = "urn:esg:security:attribute-service";

    private YadisResolver mockYadisResolver;

    private YadisResult mockYadisResult;

    private XrdsServiceEndpoint mockXrdsServiceEndpoint;

    private List<XrdsServiceEndpoint> mockEndpointList;

    @Before
    public void setupMocks() {

        this.mockYadisResolver = mock(YadisResolver.class);

        this.mockYadisResult = mock(YadisResult.class);

        this.mockXrdsServiceEndpoint = mock(XrdsServiceEndpoint.class);

        this.mockEndpointList = mock(List.class);

        when(mockYadisResult.getEndpoints()).thenReturn(mockEndpointList);

        when(mockEndpointList.get(0)).thenReturn(mockXrdsServiceEndpoint);

        when(mockXrdsServiceEndpoint.getUri()).thenReturn(SAML_ATTRIBUTE_ENDPOINT);
    }

    @Test
    public void testGetExistingSamlAttributeEndpoint() throws DiscoveryException {

        when(mockYadisResolver.discover(eq(OPENID_TESTUSER), anyInt(), (HttpCache) anyObject(), anySet())).thenReturn(mockYadisResult);


        RemoteYadisProxyImpl remoteYadisProxyImpl = new RemoteYadisProxyImpl(mockYadisResolver);

        URI samlAttributeEndpoint = remoteYadisProxyImpl.getSamlAttributeEndpoint(OPENID_TESTUSER);


        assertThat(samlAttributeEndpoint, equalTo(URI.create(SAML_ATTRIBUTE_ENDPOINT)));
    }

    @Test
    public void testCorrectServiceTypeRequested() throws DiscoveryException {

        ArgumentCaptor<Set> serviceTypeCapture = ArgumentCaptor.forClass(Set.class);

        when(mockYadisResolver.discover(eq(OPENID_TESTUSER), anyInt(), (HttpCache) anyObject(), serviceTypeCapture.capture())).thenReturn(mockYadisResult);


        RemoteYadisProxyImpl remoteYadisProxyImpl = new RemoteYadisProxyImpl(mockYadisResolver);

        remoteYadisProxyImpl.getSamlAttributeEndpoint(OPENID_TESTUSER);

        // http://weblogs.java.net/blog/johnsmart/archive/2008/04/on_the_subtle_u.html
        assertThat((Set<Object>) serviceTypeCapture.getValue(), hasItem(is(SERVICE_TYPE)));
    }

}
