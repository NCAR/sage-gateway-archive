package sgf.gateway.service.geocode.ipinfodb;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.service.geocode.ipinfodb.jackson.IpinfodbResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IpinfodbRemoteGeoCodeFacadeTest {

    private MockRestTemplate restTemplate;
    private String uriTemplate;
    private IpinfodbRemoteGeoCodeFacade facade;

    @Before
    public void setup() {
        restTemplate = new MockRestTemplate();
        uriTemplate = "http://api.ipinfodb.com/v3/ip-city/?key=db2c68c182028beafd90dc331816e4c64fa1396307c5ad1fa539f4705b9a5857&ip={0}&format=json";
        facade = new IpinfodbRemoteGeoCodeFacade(restTemplate, uriTemplate);
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
        public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {

            this.url = url;

            if (urlVariables.length == 1) {
                this.ipAddress = (String) urlVariables[0];
            }

            IpinfodbResponse response = new IpinfodbResponse();
            response.setCountryName("StubCountry");

            return (T) response;
        }

        public String getUrl() {
            return this.url;
        }

        public String getIpAddress() {
            return this.ipAddress;
        }
    }
}
