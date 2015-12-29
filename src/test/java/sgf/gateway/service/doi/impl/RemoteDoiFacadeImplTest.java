package sgf.gateway.service.doi.impl;

import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.doi.DataciteDoiRequest;
import sgf.gateway.service.doi.DoiMetadata;
import sgf.gateway.service.doi.RemoteDoiFacade;

import java.net.URI;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class RemoteDoiFacadeImplTest {

    @Test
    public void testMintDoi() {

        ResponseEntity<String> mockResponseEntity = mock(ResponseEntity.class);
        when(mockResponseEntity.getBody()).thenReturn("success: doi:10.5072/FK26H4W7W | ark:/b5072/fk26h4w7w");

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.exchange(eq("https://ezid.cdlib.org/shoulder/doi:10.5072/FK2"), eq(HttpMethod.POST), org.mockito.Matchers.isA(HttpEntity.class), eq(String.class))).thenReturn(mockResponseEntity);

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getBaseSecureURL()).thenReturn(URI.create("https://baseurl/"));

        RemoteDoiFacade remoteDoiFacade = new RemoteDoiFacadeImpl(mockRestTemplate, mockGateway, "https://ezid.cdlib.org/shoulder/doi:10.5072/FK2", "");

        DataciteDoiRequest mockDataciteRequest = mock(DataciteDoiRequest.class);
        when(mockDataciteRequest.getDatasetIdentifier()).thenReturn(UUID.valueOf("9d3e8cd0-a320-11e3-a5e2-0800200c9a66"));

        String doi = remoteDoiFacade.mintDoi(mockDataciteRequest);

        assertThat(doi, is(equalTo("doi:10.5072/FK26H4W7W")));
    }

    @Test
    public void testUpdateDoi() {

        ResponseEntity<String> mockResponseEntity = mock(ResponseEntity.class);
        when(mockResponseEntity.getBody()).thenReturn("success: doi:10.5072/FK26H4W7W");

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.exchange(eq("https://ezid.cdlib.org/id/doi:10.5072/FK26H4W7W"), eq(HttpMethod.POST), org.mockito.Matchers.isA(HttpEntity.class), eq(String.class))).thenReturn(mockResponseEntity);

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getBaseSecureURL()).thenReturn(URI.create("https://baseurl/"));

        RemoteDoiFacade remoteDoiFacade = new RemoteDoiFacadeImpl(mockRestTemplate, mockGateway, "", "");

        DataciteDoiRequest mockDataciteRequest = mock(DataciteDoiRequest.class);
        when(mockDataciteRequest.getDatasetIdentifier()).thenReturn(UUID.valueOf("9d3e8cd0-a320-11e3-a5e2-0800200c9a66"));
        when(mockDataciteRequest.getDoi()).thenReturn("doi:10.5072/FK26H4W7W");

        remoteDoiFacade.updateDoi(mockDataciteRequest);

        verify(mockRestTemplate).exchange(eq("https://ezid.cdlib.org/id/doi:10.5072/FK26H4W7W"), eq(HttpMethod.POST), org.mockito.Matchers.isA(HttpEntity.class), eq(String.class));
    }

    @Test
    public void testGetDoi() {

        ResponseEntity<String> mockResponseEntity = mock(ResponseEntity.class);
        when(mockResponseEntity.getBody()).thenReturn(this.getDoiGetResponseBody());

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.exchange(eq("https://ezid.cdlib.org/id/doi:10.5072/FK26H4W7W"), eq(HttpMethod.GET), org.mockito.Matchers.isA(HttpEntity.class), eq(String.class))).thenReturn(mockResponseEntity);

        RemoteDoiFacade remoteDoiFacade = new RemoteDoiFacadeImpl(mockRestTemplate, null, "", "");

        DoiMetadata dataciteDoi = remoteDoiFacade.getDoiMetadata("doi:10.5072/FK26H4W7W");

        assertThat("test creator", is(equalTo(dataciteDoi.getCreator())));
        assertThat("test title", is(equalTo(dataciteDoi.getTitle())));
        assertThat("test publisher", is(equalTo(dataciteDoi.getPublisher())));
        assertThat("2014", is(equalTo(dataciteDoi.getPublicationYear())));
        assertThat("Collection", is(equalTo(dataciteDoi.getResourceType())));
    }

    private String getDoiGetResponseBody() {

        StringBuilder responseBody = new StringBuilder();

        responseBody.append("success: doi:10.5072/FK2QN6KJQ\n");
        responseBody.append("_updated: 1393610959\n");
        responseBody.append("datacite.publisher: test publisher\n");
        responseBody.append("datacite.title: test title\n");
        responseBody.append("datacite.resourcetype: Collection\n");
        responseBody.append("_target: http://ezid.cdlib.org/id/doi:10.5072/FK2QN6KJQ\n");
        responseBody.append("_profile: datacite\n");
        responseBody.append("_ownergroup: apitest\n");
        responseBody.append("_owner: apitest\n");
        responseBody.append("_shadowedby: ark:/b5072/fk2qn6kjq\n");
        responseBody.append("_export: yes\n");
        responseBody.append("_created: 1393283324\n");
        responseBody.append("datacite.publicationyear: 2014\n");
        responseBody.append("datacite.creator: test creator\n");
        responseBody.append("_status: public\n");

        return responseBody.toString();
    }
}
