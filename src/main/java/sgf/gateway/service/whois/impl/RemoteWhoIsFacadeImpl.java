package sgf.gateway.service.whois.impl;

import org.springframework.web.client.RestTemplate;
import sgf.gateway.service.whois.RemoteWhoIsFacade;
import sgf.gateway.service.whois.impl.jackson.WhoIs;

public class RemoteWhoIsFacadeImpl implements RemoteWhoIsFacade {

    private RestTemplate restTemplate;

    public RemoteWhoIsFacadeImpl(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public String getNameByIPAddress(String ipAddress) {

        WhoIs whoIs = restTemplate.getForObject("http://whois.arin.net/rest/ip/{0}.json", WhoIs.class, ipAddress);

        String name = null;

        if (whoIs.getNet().getCustomerRef() != null) {

            name = whoIs.getNet().getCustomerRef().getName();

        } else if (whoIs.getNet().getOrgRef() != null) {

            name = whoIs.getNet().getOrgRef().getName();

        } else if (whoIs.getNet().getParentNetRef() != null) {

            name = whoIs.getNet().getParentNetRef().getName();
        }

        return name;
    }
}
