package sgf.gateway.service.share;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.share.jackson.ShareRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoteShareDataTest {

    Dataset mockDataset = mock(Dataset.class);
    StubRestTemplate stubRestTemplate;
    String shareUrl = "http://www.test.com";

    String authToken = "xyz";
    ShareRequestFactory mockShareRequestFactory = mock(ShareRequestFactory.class);

    @Before
    public void setup() {
        stubRestTemplate = new StubRestTemplate();
        when(mockShareRequestFactory.createShareRequest(mockDataset)).thenReturn(new ShareRequest());

    }

    @Test
    public void restTemplateIsCalledTest () {

        RemoteShareDataFacadeImpl facade = new RemoteShareDataFacadeImpl(stubRestTemplate, authToken, shareUrl, mockShareRequestFactory);

        facade.pushToShare(mockDataset);

        assertThat(stubRestTemplate.getShareUrl(), is(shareUrl));
        assertThat(stubRestTemplate.getRequest(), any(HttpEntity.class));
        assertThat(stubRestTemplate.getClazz().toString(), is("class sgf.gateway.service.share.jackson.ShareResponse"));
    }

    private class StubRestTemplate extends RestTemplate {

        private String shareUrl;
        private HttpEntity request;
        private Class clazz;

        @Override
        public <T> T postForObject(String url, Object request, java.lang.Class<T> clazz, Object... uriVariables) {

            this.shareUrl = url;
            this.request = (HttpEntity) request;
            this.clazz = clazz;

            return null;

        }

        public String getShareUrl() {
            return this.shareUrl;
        }

        public HttpEntity getRequest() {
            return request;
        }

        public Class getClazz() {
            return clazz;
        }
    }


}