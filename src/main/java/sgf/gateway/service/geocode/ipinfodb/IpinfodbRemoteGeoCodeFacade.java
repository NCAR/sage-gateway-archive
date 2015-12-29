package sgf.gateway.service.geocode.ipinfodb;

import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;
import sgf.gateway.service.geocode.ipinfodb.jackson.IpinfodbResponse;

public class IpinfodbRemoteGeoCodeFacade implements RemoteGeoCodeFacade {

    private RestTemplate restTemplate;
    private String uriTemplate;

    public IpinfodbRemoteGeoCodeFacade(RestTemplate restTemplate, String uriTemplate) {
        this.restTemplate = restTemplate;
        this.uriTemplate = uriTemplate;
    }

    @Override
    public GeoCode getForIpAddress(String ipAddress) {
        return new IpinfodbGeoCode(getIpinfodbResponse(ipAddress));
    }

    private IpinfodbResponse getIpinfodbResponse(String ipAddress) {
        return restTemplate.getForObject(uriTemplate, IpinfodbResponse.class, ipAddress);
    }
}
