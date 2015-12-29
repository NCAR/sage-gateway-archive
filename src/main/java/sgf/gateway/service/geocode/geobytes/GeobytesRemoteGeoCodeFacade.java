package sgf.gateway.service.geocode.geobytes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sgf.gateway.model.metrics.GeoCode;
import sgf.gateway.service.geocode.RemoteGeoCodeFacade;
import sgf.gateway.service.geocode.geobytes.jackson.GeobytesResponse;

import java.io.IOException;

public class GeobytesRemoteGeoCodeFacade implements RemoteGeoCodeFacade {

    private RestTemplate restTemplate;
    private String uriTemplate;
    private ObjectMapper mapper;

    public GeobytesRemoteGeoCodeFacade(RestTemplate restTemplate, String uriTemplate) {
        this.restTemplate = restTemplate;
        this.uriTemplate = uriTemplate;
        this.mapper = new ObjectMapper();
    }

    @Override
    public GeoCode getForIpAddress(String ipAddress) {
        return new GeobytesGeoCode(getGeobytesResponse(ipAddress));
    }

    private GeobytesResponse getGeobytesResponse(String ipAddress) {

        // using getForEntity instead of getForObject because www.getbytes.com sets the content
        // type to "text/html" instead of "application/json" which is required for the converter
        // MappingJackson2HttpMessageConverter to build GeobytesResponse

        ResponseEntity responseEntity = restTemplate.getForEntity(uriTemplate, String.class, ipAddress);

        try {
            return mapper.readValue((String) responseEntity.getBody(), GeobytesResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
