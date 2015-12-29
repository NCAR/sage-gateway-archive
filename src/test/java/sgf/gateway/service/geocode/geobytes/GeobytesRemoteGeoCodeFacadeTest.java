package sgf.gateway.service.geocode.geobytes;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GeobytesRemoteGeoCodeFacadeTest {

    private MockRestTemplate restTemplate;
    private String uriTemplate;
    private GeobytesRemoteGeoCodeFacade facade;

    @Before
    public void setup() {
        restTemplate = new MockRestTemplate();
        uriTemplate = "http://www.geobytes.com/IpLocator.htm?GetLocation&template=json.txt&ipaddress={0}";
        facade = new GeobytesRemoteGeoCodeFacade(restTemplate, uriTemplate);
    }

    @Test
    public void testRestTemplateIsCalled() {

        facade.getForIpAddress("128.117.11.135");

        assertThat("128.117.11.135", is(restTemplate.getIpAddress()));
        assertThat(uriTemplate, is(restTemplate.getUrl()));
    }

    @Test
    public void testGeoCodeIsReturned() {
        assertThat(facade.getForIpAddress(null).getCountry(), is("StubCountry"));
    }

    private class MockRestTemplate extends RestTemplate {

        private String ipAddress;
        private String url;

        @Override
        public ResponseEntity getForEntity(String url, Class responseType, Object... urlVariables) {

            this.url = url;

            if (urlVariables.length == 1) {
                this.ipAddress = (String) urlVariables[0];
            }

            ResponseEntity<String> responseEntity = new ResponseEntity<>("{\"geobytes\" : {\"country\" : \"StubCountry\"}}", HttpStatus.OK);

            return responseEntity;
        }

        public String getUrl() {
            return this.url;
        }

        public String getIpAddress() {
            return this.ipAddress;
        }
    }
}
