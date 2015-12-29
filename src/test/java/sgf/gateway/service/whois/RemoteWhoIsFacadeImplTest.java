package sgf.gateway.service.whois;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.service.whois.impl.RemoteWhoIsFacadeImpl;
import sgf.gateway.service.whois.impl.jackson.Net;
import sgf.gateway.service.whois.impl.jackson.OrgRef;
import sgf.gateway.service.whois.impl.jackson.WhoIs;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RemoteWhoIsFacadeImplTest {

    @Test
    public void testRestTemplateIsCalled() {

        MockRestTemplate mockRestTemplate = new MockRestTemplate();
        RemoteWhoIsFacadeImpl whoIsFacade = new RemoteWhoIsFacadeImpl(mockRestTemplate);

        whoIsFacade.getNameByIPAddress("128.117.11.111");

        assertThat("128.117.11.111", is(mockRestTemplate.getIpAddress()));
        assertThat("http://whois.arin.net/rest/ip/{0}.json", is(mockRestTemplate.getUrl()));
    }

    @Test
    public void testNameIsReturned() {

        MockRestTemplate mockRestTemplate = new MockRestTemplate();
        RemoteWhoIsFacadeImpl whoIsFacade = new RemoteWhoIsFacadeImpl(mockRestTemplate);

        assertThat("National Center for Atmospheric Research", is(whoIsFacade.getNameByIPAddress("128.117.11.111")));
    }

    public class MockRestTemplate extends RestTemplate {

        private String ipAddress;
        private String url;

        public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) {

            this.url = url;

            if (urlVariables.length == 1) {
                this.ipAddress = (String) urlVariables[0];
            }

            OrgRef orgRef = new OrgRef();
            orgRef.setName("National Center for Atmospheric Research");

            Net net = new Net();
            net.setOrgRef(orgRef);

            WhoIs whoIs = new WhoIs();
            whoIs.setNet(net);

            return (T) whoIs;
        }

        public String getUrl() {
            return this.url;
        }

        public String getIpAddress() {
            return this.ipAddress;
        }
    }
}
